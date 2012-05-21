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
package fr.peralta.mycellar.infrastructure.stack.repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;
import fr.peralta.mycellar.domain.stack.repository.StackOrderEnum;
import fr.peralta.mycellar.domain.stack.repository.StackRepository;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaStackRepository extends JpaEntityRepository<Stack, StackOrderEnum, StackOrder>
        implements StackRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllStacks() {
        getEntityManager().createQuery("DELETE " + Stack.class.getSimpleName()).executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stack getByHashCode(int hashCode) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Stack> query = criteriaBuilder.createQuery(Stack.class);
        Root<Stack> root = query.from(Stack.class);

        try {
            return getEntityManager()
                    .createQuery(
                            query.select(root).where(
                                    criteriaBuilder.equal(root.get("hashCode"), hashCode)))
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Stack> root, StackOrderEnum order, JoinType joinType) {
        switch (order) {
        case COUNT:
            return root.get("count");
        default:
            throw new IllegalStateException("Unknown " + StackOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Stack> getEntityClass() {
        return Stack.class;
    }

}
