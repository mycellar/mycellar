/*
 * Copyright 2013, MyCellar
 *
 * This file is part of MyCellar.
 *
 * MyCellar is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCellar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCellar. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.mycellar.infrastructure.shared.repository.util;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.annotation.Lazy;

import fr.mycellar.domain.shared.Identifiable;
import fr.mycellar.infrastructure.shared.repository.query.SearchMode;
import fr.mycellar.infrastructure.shared.repository.query.SearchParametersValues;

@Named
@Singleton
@Lazy(false)
public class JpaUtil {

    private static JpaUtil instance;

    public static JpaUtil getInstance() {
        return instance;
    }

    public JpaUtil() {
        instance = this;
    }

    public Predicate andPredicate(CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return andPredicate(builder, Arrays.asList(predicatesNullAllowed));
    }

    public Predicate orPredicate(CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return orPredicate(builder, Arrays.asList(predicatesNullAllowed));
    }

    public Predicate andPredicate(CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        List<Predicate> predicates = newArrayList(filter(predicatesNullAllowed, notNull()));
        if ((predicates == null) || predicates.isEmpty()) {
            return null;
        } else if (predicates.size() == 1) {
            return predicates.get(0);
        } else {
            return builder.and(toArray(predicates, Predicate.class));
        }
    }

    public Predicate orPredicate(CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        List<Predicate> predicates = newArrayList(filter(predicatesNullAllowed, notNull()));
        if ((predicates == null) || predicates.isEmpty()) {
            return null;
        } else if (predicates.size() == 1) {
            return predicates.get(0);
        } else {
            return builder.or(toArray(predicates, Predicate.class));
        }
    }

    public <E> Predicate stringPredicate(Expression<String> path, Object attrValue, SearchMode searchMode, boolean caseSensitive, CriteriaBuilder builder) {
        if (!caseSensitive) {
            path = builder.lower(path);
            attrValue = ((String) attrValue).toLowerCase(Locale.FRANCE);
        }

        switch (searchMode) {
        case EQUALS:
            return builder.equal(path, attrValue);
        case ENDING_LIKE:
            return builder.like(path, "%" + attrValue);
        case STARTING_LIKE:
            return builder.like(path, attrValue + "%");
        case ANYWHERE:
            return builder.like(path, "%" + attrValue + "%");
        case LIKE:
            // assume user provide the wild cards
            return builder.like(path, (String) attrValue);
        default:
            throw new IllegalStateException("expecting a search mode!");
        }
    }

    /**
     * Convert the passed propertyPath into a JPA path.
     * <p>
     * Note: JPA will do joins if the property is in an associated entity.
     */
    @SuppressWarnings("unchecked")
    public <E, F> Path<F> getPath(Root<E> root, List<Attribute<?, ?>> attributes) {
        Path<?> path = root;
        for (Attribute<?, ?> attribute : attributes) {
            boolean found = false;
            if (path instanceof FetchParent) {
                for (Fetch<E, ?> fetch : ((FetchParent<?, E>) path).getFetches()) {
                    if (attribute.getName().equals(fetch.getAttribute().getName()) && (fetch instanceof Join<?, ?>)) {
                        path = (Join<E, ?>) fetch;
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                if (attribute instanceof PluralAttribute) {
                    path = ((From<?, ?>) path).join(attribute.getName(), JoinType.LEFT);
                } else {
                    path = path.get(attribute.getName());
                }
            }
        }
        return (Path<F>) path;
    }

    public void verifyPath(Attribute<?, ?>... path) {
        verifyPath(newArrayList(path));
    }

    public void verifyPath(List<Attribute<?, ?>> path) {
        List<Attribute<?, ?>> attributes = new ArrayList<>(path);
        Class<?> from = null;
        if (attributes.get(0).isCollection()) {
            from = ((PluralAttribute<?, ?, ?>) attributes.get(0)).getElementType().getJavaType();
        } else {
            from = attributes.get(0).getJavaType();
        }
        attributes.remove(0);
        for (Attribute<?, ?> attribute : attributes) {
            if (!attribute.getDeclaringType().getJavaType().isAssignableFrom(from)) {
                throw new IllegalStateException("Wrong path.");
            }
            from = attribute.getJavaType();
        }
    }

    public <T, A> SingularAttribute<? super T, A> attribute(ManagedType<? super T> mt, Attribute<? super T, A> attr) {
        return mt.getSingularAttribute(attr.getName(), attr.getJavaType());
    }

    public <T> SingularAttribute<? super T, String> stringAttribute(ManagedType<? super T> mt, Attribute<? super T, ?> attr) {
        return mt.getSingularAttribute(attr.getName(), String.class);
    }

    public String[] toNames(Attribute<?, ?>... attributes) {
        return toNamesList(Arrays.asList(attributes)).toArray(new String[0]);
    }

    public List<String> toNamesList(List<Attribute<?, ?>> attributes) {
        List<String> ret = new ArrayList<>();
        for (Attribute<?, ?> attribute : attributes) {
            ret.add(attribute.getName());
        }
        return ret;
    }

    public String getEntityName(Identifiable<?> entity) {
        Entity entityAnnotation = entity.getClass().getAnnotation(Entity.class);
        if (isBlank(entityAnnotation.name())) {
            return getClassWithoutInitializingProxy(entity).getSimpleName();
        }
        return entityAnnotation.name();
    }

    public String methodToProperty(Method m) {
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(m.getDeclaringClass());
        for (PropertyDescriptor pd : pds) {
            if (m.equals(pd.getReadMethod()) || m.equals(pd.getWriteMethod())) {
                return pd.getName();
            }
        }
        return null;
    }

    public void applyPagination(Query query, SearchParametersValues<?> sp) {
        if (sp.getFirstResult() > 0) {
            query.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            query.setMaxResults(sp.getMaxResults());
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <E> void fetches(SearchParametersValues<E> sp, Root<E> root) {
        for (fr.mycellar.infrastructure.shared.repository.query.Path<E, ?> args : sp.getFetches()) {
            FetchParent<?, ?> from = root;
            for (Attribute<?, ?> arg : args.getAttributes()) {
                boolean found = false;
                for (Fetch<?, ?> fetch : from.getFetches()) {
                    if (arg.equals(fetch.getAttribute())) {
                        from = fetch;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (arg instanceof PluralAttribute) {
                        from = from.fetch((PluralAttribute) arg, JoinType.LEFT);
                    } else {
                        from = from.fetch((SingularAttribute) arg, JoinType.LEFT);
                    }
                }
            }
        }
    }

}