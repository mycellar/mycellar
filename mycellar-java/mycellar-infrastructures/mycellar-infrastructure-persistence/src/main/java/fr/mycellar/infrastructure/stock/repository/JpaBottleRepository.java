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
package fr.mycellar.infrastructure.stock.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.repository.BottleRepository;
import fr.mycellar.infrastructure.shared.repository.JpaSimpleRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaBottleRepository extends JpaSimpleRepository<Bottle> implements BottleRepository {

    /**
     * Default constructor.
     */
    public JpaBottleRepository() {
        super(Bottle.class);
    }

}
