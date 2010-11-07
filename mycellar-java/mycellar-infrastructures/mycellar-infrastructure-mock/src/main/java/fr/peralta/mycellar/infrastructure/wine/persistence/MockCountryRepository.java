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
package fr.peralta.mycellar.infrastructure.wine.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.property.DirectPropertyAccessor;

import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.CountryRepository;

/**
 * @author speralta
 */
public class MockCountryRepository implements CountryRepository {

    private final String[] names = new String[] { "France", "Espagne", "Italie", "Hongrie",
            "Portugal", "Allemagne", "Etats-unis", "Afrique du Sud", "Argentine", "Belgique" };

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Country> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Integer> getAllWithCounts() {
        Map<Country, Integer> result = new HashMap<Country, Integer>();
        for (int i = 0; i < 10; i++) {
            Country country = new Country(names[i], "", "");
            new DirectPropertyAccessor().getSetter(Country.class, "id").set(country, i, null);
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(country, 23 % a);
        }
        return result;
    }

}
