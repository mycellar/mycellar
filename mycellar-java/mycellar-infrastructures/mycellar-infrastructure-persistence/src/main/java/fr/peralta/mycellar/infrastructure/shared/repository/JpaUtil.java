/*
 * Copyright 2011, MyCellar
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
package fr.peralta.mycellar.infrastructure.shared.repository;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Lists.newArrayList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxyHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import fr.peralta.mycellar.domain.shared.Identifiable;
import fr.peralta.mycellar.domain.shared.repository.SearchMode;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

public class JpaUtil {

    public static boolean isEntityIdManuallyAssigned(Class<?> type) {
        for (Method method : type.getMethods()) {
            if (isPrimaryKey(method)) {
                return isManuallyAssigned(method);
            }
        }
        return false; // no pk found, should not happen
    }

    private static boolean isPrimaryKey(Method method) {
        return Modifier.isPublic(method.getModifiers()) && ((method.getAnnotation(Id.class) != null) || (method.getAnnotation(EmbeddedId.class) != null));
    }

    private static boolean isManuallyAssigned(Method method) {
        if (method.getAnnotation(Id.class) != null) {
            return method.getAnnotation(GeneratedValue.class) == null;
        } else if (method.getAnnotation(EmbeddedId.class) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static Predicate concatPredicate(SearchParameters sp, CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return concatPredicate(sp, builder, Arrays.asList(predicatesNullAllowed));
    }

    public static Predicate concatPredicate(SearchParameters sp, CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        if (sp.isAndMode()) {
            return andPredicate(builder, predicatesNullAllowed);
        } else {
            return orPredicate(builder, predicatesNullAllowed);
        }
    }

    public static Predicate andPredicate(CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return andPredicate(builder, Arrays.asList(predicatesNullAllowed));
    }

    public static Predicate orPredicate(CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return orPredicate(builder, Arrays.asList(predicatesNullAllowed));
    }

    public static Predicate andPredicate(CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        List<Predicate> predicates = newArrayList(filter(predicatesNullAllowed, notNull()));
        if ((predicates == null) || predicates.isEmpty()) {
            return null;
        } else if (predicates.size() == 1) {
            return predicates.get(0);
        } else {
            return builder.and(toArray(predicates, Predicate.class));
        }
    }

    public static Predicate orPredicate(CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        List<Predicate> predicates = newArrayList(filter(predicatesNullAllowed, notNull()));
        if ((predicates == null) || predicates.isEmpty()) {
            return null;
        } else if (predicates.size() == 1) {
            return predicates.get(0);
        } else {
            return builder.or(toArray(predicates, Predicate.class));
        }
    }

    public static <E> Predicate stringPredicate(Expression<String> path, Object attrValue, SearchMode searchMode, SearchParameters sp, CriteriaBuilder builder) {
        if (!sp.isCaseSensitive()) {
            path = builder.lower(path);
            attrValue = ((String) attrValue).toLowerCase(LocaleContextHolder.getLocale());
        }

        switch (searchMode != null ? searchMode : sp.getSearchMode()) {
        case EQUALS:
            return builder.equal(path, attrValue);
        case ENDING_LIKE:
            return builder.like(path, "%" + attrValue);
        case STARTING_LIKE:
            return builder.like(path, attrValue + "%");
        case ANYWHERE:
            return builder.like(path, "%" + attrValue + "%");
        case LIKE:
            return builder.like(path, (String) attrValue); // assume user
                                                           // provide the wild
                                                           // cards
        default:
            throw new IllegalStateException("expecting a search mode!");
        }
    }

    public static <E> Predicate stringPredicate(Expression<String> path, Object attrValue, SearchParameters sp, CriteriaBuilder builder) {
        return stringPredicate(path, attrValue, null, sp, builder);
    }

    /**
     * Convert the passed propertyPath into a JPA path.
     * <p>
     * Note: JPA will do joins if the property is in an associated entity.
     */
    @SuppressWarnings("unchecked")
    public static <E, F> Path<F> getPath(Root<E> root, List<Attribute<?, ?>> attributes) {
        Path<?> path = root;
        for (Attribute<?, ?> attribute : attributes) {
            boolean found = false;
            // handle case when order on already fetched attribute
            for (Fetch<E, ?> fetch : root.getFetches()) {
                if (attribute.getName().equals(fetch.getAttribute().getName()) && (fetch instanceof Join<?, ?>)) {
                    path = (Join<E, ?>) fetch;
                    found = true;
                    break;
                }
            }
            for (Join<E, ?> join : root.getJoins()) {
                if (attribute.getName().equals(join.getAttribute().getName())) {
                    path = join;
                    found = true;
                    break;
                }
            }
            if (!found) {
                path = path.get(attribute.getName());
            }
        }
        return (Path<F>) path;
    }

    public static String getPath(List<Attribute<?, ?>> attributes) {
        StringBuilder builder = new StringBuilder();
        for (Attribute<?, ?> attribute : attributes) {
            builder.append(attribute.getName()).append(".");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public static <T extends Identifiable<?>> String compositePkPropertyName(T entity) {
        for (Method m : entity.getClass().getMethods()) {
            if (m.getAnnotation(EmbeddedId.class) != null) {
                return BeanUtils.findPropertyForMethod(m).getName();
            }
        }
        for (Field f : entity.getClass().getFields()) {
            if (f.getAnnotation(EmbeddedId.class) != null) {
                return f.getName();
            }
        }
        return null;
    }

    public static <T> boolean isPk(ManagedType<T> mt, SingularAttribute<? super T, ?> attr) {
        try {
            Method m = BeanUtils.findMethod(mt.getJavaType(), "get" + WordUtils.capitalize(attr.getName()));
            if ((m != null) && (m.getAnnotation(Id.class) != null)) {
                return true;
            }

            Field field = mt.getJavaType().getField(attr.getName());
            return field.getAnnotation(Id.class) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static <T> Object getValue(T example, Attribute<? super T, ?> attr) {
        try {
            if (attr.getJavaMember() instanceof Method) {
                return ReflectionUtils.invokeMethod((Method) attr.getJavaMember(), example);
            } else {
                return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
            }
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    public static <T, A> SingularAttribute<? super T, A> attribute(ManagedType<? super T> mt, Attribute<? super T, A> attr) {
        return mt.getSingularAttribute(attr.getName(), attr.getJavaType());
    }

    public static <T> SingularAttribute<? super T, String> stringAttribute(ManagedType<? super T> mt, Attribute<? super T, ?> attr) {
        return mt.getSingularAttribute(attr.getName(), String.class);
    }

    public static <T extends Identifiable<?>> boolean hasSimplePk(Class<T> entityClass) {
        for (Method m : entityClass.getMethods()) {
            if (m.getAnnotation(Id.class) != null) {
                return true;
            }
        }
        for (Field f : entityClass.getFields()) {
            if (f.getAnnotation(Id.class) != null) {
                return true;
            }
        }
        return false;
    }

    public static String[] toNames(SingularAttribute<?, ?>... attributes) {
        List<String> ret = newArrayList();
        for (SingularAttribute<?, ?> attribute : attributes) {
            ret.add(attribute.getName());
        }
        return ret.toArray(new String[ret.size()]);
    }

    public static List<String> toNamesList(List<SingularAttribute<?, ?>> attributes) {
        List<String> ret = newArrayList();
        for (SingularAttribute<?, ?> attribute : attributes) {
            ret.add(attribute.getName());
        }
        return ret;
    }

    public static String getEntityName(Identifiable<?> entity) {
        Entity entityAnnotation = AnnotationUtils.findAnnotation(entity.getClass(), Entity.class);
        if (StringUtils.isBlank(entityAnnotation.name())) {
            return HibernateProxyHelper.getClassWithoutInitializingProxy(entity).getSimpleName();
        }
        return entityAnnotation.name();
    }

    public static String methodToProperty(Method m) {
        return BeanUtils.findPropertyForMethod(m).getName();
    }

    public static Object getValueFromField(Field field, Object object) {
        boolean accessible = field.isAccessible();
        try {
            return ReflectionUtils.getField(field, object);
        } finally {
            field.setAccessible(accessible);
        }
    }

    public static void applyPagination(Query query, SearchParameters sp) {
        if (sp.getFirstResult() > 0) {
            query.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            query.setMaxResults(sp.getMaxResults());
        }
    }

    public static void applyCacheHints(Query query, SearchParameters sp, Class<? extends Identifiable<?>> type) {
        if (sp.isCacheable()) {
            query.setHint("org.hibernate.cacheable", true);

            if (StringUtils.isNotBlank(sp.getCacheRegion())) {
                query.setHint("org.hibernate.cacheRegion", sp.getCacheRegion());
            } else {
                query.setHint("org.hibernate.cacheRegion", type.getCanonicalName());
            }
        }
    }
}