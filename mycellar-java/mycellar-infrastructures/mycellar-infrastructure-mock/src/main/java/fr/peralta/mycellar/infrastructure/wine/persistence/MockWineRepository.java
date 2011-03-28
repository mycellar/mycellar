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
import java.util.Map;

import org.hibernate.property.DirectPropertyAccessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineRepository;

/**
 * @author speralta
 */

@Repository
@Qualifier("mock")
public class MockWineRepository implements WineRepository {

    private final String[] countries = new String[] { "France", "Espagne", "Italie", "Hongrie",
            "Portugal", "Allemagne", "Etats-unis", "Afrique du Sud", "Argentine", "Belgique" };
    private final String[][] regions = new String[][] { new String[] { "Bourgogne", "Bordeaux" },
            new String[] { "Espagne" }, new String[] { "Italie" }, new String[] { "Hongrie" },
            new String[] { "Portugal" }, new String[] { "Allemagne" },
            new String[] { "Etats-unis" }, new String[] { "Afrique du Sud" },
            new String[] { "Argentine" }, new String[] { "Belgique" } };
    private final String[][] appellations = new String[][] {
            new String[] { "Chambertin", "Meursault" }, new String[] { "Pomerol" },
            new String[] { "Italie" }, new String[] { "Hongrie" }, new String[] { "Portugal" },
            new String[] { "Allemagne" }, new String[] { "Etats-unis" },
            new String[] { "Afrique du Sud" }, new String[] { "Argentine" },
            new String[] { "Belgique" } };

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Integer> getAllCountriesWithCounts() {
        Map<Country, Integer> result = new HashMap<Country, Integer>();
        for (int i = 0; i < countries.length; i++) {
            Country country = new Country();
            country.setName(countries[i]);
            new DirectPropertyAccessor().getSetter(Country.class, "id").set(country, i, null);
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(country, 23 % a);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Region, Integer> getAllRegionsFromCountryWithCounts(Country country) {
        Map<Region, Integer> result = new HashMap<Region, Integer>();
        int position = 0;
        for (int i = 0; i < countries.length; i++) {
            if (countries[i].equals(country.getName())) {
                position = i;
                break;
            }
        }
        for (int i = 0; i < regions[position].length; i++) {
            Region region = new Region();
            region.setName(regions[position][i]);
            region.setCountry(country);
            new DirectPropertyAccessor().getSetter(Region.class, "id").set(region, i, null);
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(region, 23 % a);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Appellation, Integer> getAllAppellationsFromRegionWithCounts(Region region) {
        Map<Appellation, Integer> result = new HashMap<Appellation, Integer>();
        int position = 0;
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[i].length; j++) {
                if (regions[i][j].equals(region.getName())) {
                    position = i + j;
                    break;
                }
            }
        }
        for (int i = 0; i < appellations[position].length; i++) {
            Appellation appellation = new Appellation();
            appellation.setName(appellations[position][i]);
            appellation.setRegion(region);
            new DirectPropertyAccessor().getSetter(Appellation.class, "id").set(appellation, i,
                    null);
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(appellation, 23 % a);
        }
        return result;
    }

}
