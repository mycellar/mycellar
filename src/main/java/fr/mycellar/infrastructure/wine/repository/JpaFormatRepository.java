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
package fr.mycellar.infrastructure.wine.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import jpasearch.repository.JpaSimpleRepository;
import fr.mycellar.domain.wine.Format;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaFormatRepository extends JpaSimpleRepository<Format, Integer> implements FormatRepository {

    /**
     * Default constructor.
     */
    public JpaFormatRepository() {
        super(Format.class);
    }
}
