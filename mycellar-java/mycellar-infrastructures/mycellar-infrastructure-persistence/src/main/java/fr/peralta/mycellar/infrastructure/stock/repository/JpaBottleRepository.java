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
package fr.peralta.mycellar.infrastructure.stock.repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.stock.repository.BottleOrder;
import fr.peralta.mycellar.domain.stock.repository.BottleOrderEnum;
import fr.peralta.mycellar.domain.stock.repository.BottleRepository;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.infrastructure.shared.repository.JoinHelper;
import fr.peralta.mycellar.infrastructure.shared.repository.JpaEntityRepository;

/**
 * @author speralta
 */
@Repository
public class JpaBottleRepository extends JpaEntityRepository<Bottle, BottleOrderEnum, BottleOrder>
        implements BottleRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Bottle find(Wine wine, Format format) {
        if ((wine.getId() == null) || (format.getId() == null)) {
            return null;
        }

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Bottle> query = criteriaBuilder.createQuery(Bottle.class);
        Root<Bottle> root = query.from(Bottle.class);

        try {
            return getEntityManager().createQuery(
                    query.select(root).where(criteriaBuilder.equal(root.get("wine"), wine),
                            criteriaBuilder.equal(root.get("format"), format))).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Expression<?> getOrderByPath(Root<Bottle> root, BottleOrderEnum order,
            JoinType joinType) {
        switch (order) {
        case FORMAT_NAME:
            return JoinHelper.getJoin(root, "format", joinType).get("name");
        case WINE_VINTAGE:
            return JoinHelper.getJoin(root, "wine", joinType).get("vintage");
        default:
            throw new IllegalStateException("Unknown " + BottleOrderEnum.class.getSimpleName()
                    + " value [" + order + "].");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Bottle> getEntityClass() {
        return Bottle.class;
    }

}
