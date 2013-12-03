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
package fr.mycellar.infrastructure.shared.repository;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.reflect.Modifier.isPublic;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.hibernate.proxy.HibernateProxyHelper.getClassWithoutInitializingProxy;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;

import com.google.common.base.Function;

import fr.mycellar.domain.shared.Identifiable;

@Named
@Singleton
@Lazy(false)
public class JpaUtil {

    private final Map<Class<?>, String> compositePkCache = new HashMap<>();
    private static JpaUtil instance;

    public static JpaUtil getInstance() {
        return instance;
    }

    public JpaUtil() {
        instance = this;
    }

    public boolean isEntityIdManuallyAssigned(Class<?> type) {
        for (Method method : type.getMethods()) {
            if (isPrimaryKey(method)) {
                return isManuallyAssigned(method);
            }
        }
        return false; // no pk found, should not happen
    }

    private boolean isPrimaryKey(Method method) {
        return isPublic(method.getModifiers()) && ((method.getAnnotation(Id.class) != null) || (method.getAnnotation(EmbeddedId.class) != null));
    }

    private boolean isManuallyAssigned(Method method) {
        if (method.getAnnotation(Id.class) != null) {
            return method.getAnnotation(GeneratedValue.class) == null;
        }

        return method.getAnnotation(EmbeddedId.class) != null;
    }

    public Predicate concatPredicate(SearchParameters sp, CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return concatPredicate(sp, builder, Arrays.asList(predicatesNullAllowed));
    }

    public Predicate concatPredicate(SearchParameters sp, CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        if (sp.isAndMode()) {
            return andPredicate(builder, predicatesNullAllowed);
        } else {
            return orPredicate(builder, predicatesNullAllowed);
        }
    }

    public Predicate andPredicate(CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return andPredicate(builder, Arrays.asList(predicatesNullAllowed));
    }

    public Predicate andNotPredicate(CriteriaBuilder builder, Predicate... predicatesNullAllowed) {
        return andNotPredicate(builder, Arrays.asList(predicatesNullAllowed));
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

    public Predicate andNotPredicate(final CriteriaBuilder builder, Iterable<Predicate> predicatesNullAllowed) {
        List<Predicate> predicates = newArrayList(transform(filter(predicatesNullAllowed, notNull()), new Function<Predicate, Predicate>() {
            @Override
            public Predicate apply(Predicate input) {
                return builder.not(input);
            }

        }));
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

    public <E> Predicate stringPredicate(Expression<String> path, Object attrValue, SearchMode searchMode, Boolean caseSensitive, SearchParameters sp, CriteriaBuilder builder) {
        if (caseSensitive != null ? !caseSensitive : sp.isCaseInsensitive()) {
            path = builder.lower(path);
            attrValue = ((String) attrValue).toLowerCase(Locale.FRANCE);
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

    public <E> Predicate stringPredicate(Expression<String> path, Object attrValue, SearchParameters sp, CriteriaBuilder builder) {
        return stringPredicate(path, attrValue, null, null, sp, builder);
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

    public <T extends Identifiable<?>> String compositePkPropertyName(T entity) {
        Class<?> entityClass = entity.getClass();
        if (compositePkCache.containsKey(entityClass)) {
            return compositePkCache.get(entityClass);
        }

        for (Method m : entity.getClass().getMethods()) {
            if (m.getAnnotation(EmbeddedId.class) != null) {
                String propertyName = methodToProperty(m);
                compositePkCache.put(entityClass, propertyName);
                return propertyName;
            }
        }
        for (Field f : entity.getClass().getFields()) {
            if (f.getAnnotation(EmbeddedId.class) != null) {
                String propertyName = f.getName();
                compositePkCache.put(entityClass, propertyName);
                return propertyName;
            }
        }
        compositePkCache.put(entityClass, null);
        return null;
    }

    public <T> boolean isPk(ManagedType<T> mt, SingularAttribute<? super T, ?> attr) {
        try {
            Method m = MethodUtils.getAccessibleMethod(mt.getJavaType(), "get" + WordUtils.capitalize(attr.getName()), (Class<?>) null);
            if ((m != null) && (m.getAnnotation(Id.class) != null)) {
                return true;
            }

            Field field = mt.getJavaType().getField(attr.getName());
            return field.getAnnotation(Id.class) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public <T> Object getValue(T example, Attribute<? super T, ?> attr) {
        try {
            if (attr.getJavaMember() instanceof Method) {
                return ((Method) attr.getJavaMember()).invoke(example);
            } else {
                return ((Field) attr.getJavaMember()).get(example);
            }
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    public <T, A> SingularAttribute<? super T, A> attribute(ManagedType<? super T> mt, Attribute<? super T, A> attr) {
        return mt.getSingularAttribute(attr.getName(), attr.getJavaType());
    }

    public <T> SingularAttribute<? super T, String> stringAttribute(ManagedType<? super T> mt, Attribute<? super T, ?> attr) {
        return mt.getSingularAttribute(attr.getName(), String.class);
    }

    public <T extends Identifiable<?>> boolean hasSimplePk(Class<T> entityClass) {
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

    public Object getValueFromField(Field field, Object object) {
        boolean accessible = field.isAccessible();
        try {
            return getField(field, object);
        } finally {
            field.setAccessible(accessible);
        }
    }

    public void applyPagination(Query query, SearchParameters sp) {
        if (sp.getFirstResult() > 0) {
            query.setFirstResult(sp.getFirstResult());
        }
        if (sp.getMaxResults() > 0) {
            query.setMaxResults(sp.getMaxResults());
        }
    }

    public void applyCacheHints(Query query, SearchParameters sp, Class<? extends Identifiable<?>> type) {
        if (sp.isCacheable()) {
            query.setHint("org.hibernate.cacheable", true);

            if (StringUtils.isNotBlank(sp.getCacheRegion())) {
                query.setHint("org.hibernate.cacheRegion", sp.getCacheRegion());
            } else {
                query.setHint("org.hibernate.cacheRegion", type.getCanonicalName());
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void fetches(SearchParameters sp, Root<?> root) {
        for (List<Attribute<?, ?>> args : sp.getFetches()) {
            FetchParent<?, ?> from = root;
            for (Attribute<?, ?> arg : args) {
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

    @SuppressWarnings("unchecked")
    private <T> T getField(Field field, Object target) {
        try {
            return (T) field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}