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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.property.DirectPropertyAccessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineRepository;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;

/**
 * @author speralta
 */

@Repository
@Qualifier("mock")
public class MockWineRepository implements WineRepository {

    private static final Map<String, Float> formats = new HashMap<String, Float>();
    private static final String[] countries = new String[] { "France", "Espagne", "Italie",
            "Hongrie", "Portugal", "Allemagne", "Etats-unis", "Afrique du Sud", "Argentine",
            "Belgique" };
    private static final String[][] regions = new String[][] {
            new String[] { "Bourgogne", "Bordeaux" }, new String[] { "Espagne" },
            new String[] { "Italie" }, new String[] { "Hongrie" }, new String[] { "Portugal" },
            new String[] { "Allemagne" }, new String[] { "Etats-unis" },
            new String[] { "Afrique du Sud" }, new String[] { "Argentine" },
            new String[] { "Belgique" } };
    private static final String[][] appellations = new String[][] {
            new String[] { "Chambertin", "Meursault" }, new String[] { "Pomerol" },
            new String[] { "Italie" }, new String[] { "Hongrie" }, new String[] { "Portugal" },
            new String[] { "Allemagne" }, new String[] { "Etats-unis" },
            new String[] { "Afrique du Sud" }, new String[] { "Argentine" },
            new String[] { "Belgique" } };
    private static final String[] producers = new String[] { "Domaine Rousseau", "Domaine Macle",
            "Château Margaux", "Château Cheval Blanc", "Clos des papes", "Clos des lambrays",
            "Clos de tart" };
    private static final WineTypeEnum[][] types = new WineTypeEnum[][] {
            new WineTypeEnum[] { WineTypeEnum.STILL },
            new WineTypeEnum[] { WineTypeEnum.STILL, WineTypeEnum.SPARKLING },
            new WineTypeEnum[] { WineTypeEnum.STILL }, new WineTypeEnum[] { WineTypeEnum.STILL },
            new WineTypeEnum[] { WineTypeEnum.STILL }, new WineTypeEnum[] { WineTypeEnum.STILL },
            new WineTypeEnum[] { WineTypeEnum.STILL } };
    private static final WineColorEnum[][][] colors = new WineColorEnum[][][] {
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.WHITE, WineColorEnum.RED } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.RED, WineColorEnum.WHITE },
                    new WineColorEnum[] { WineColorEnum.WHITE } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.ROSE, WineColorEnum.RED } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.OTHER, WineColorEnum.RED } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.RED },
                    new WineColorEnum[] { WineColorEnum.OTHER } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.WHITE, WineColorEnum.OTHER } } };
    static {
        formats.put("Demi-bouteille", 0.375f);
        formats.put("Bouteille", 0.75f);
        formats.put("Magnum", 1.5f);
        formats.put("Double-magnum", 3f);
        formats.put("Clavelin", 0.62f);
    }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producer> getAllProducerStartingWith(String term) {
        List<Producer> result = new ArrayList<Producer>();
        int i = 1;
        for (String producerName : producers) {
            if (producerName.startsWith(term)) {
                Producer producer = new Producer();
                producer.setName(producerName);
                producer.setDescription("desc");
                new DirectPropertyAccessor().getSetter(Producer.class, "id").set(producer, i++,
                        null);
                result.add(producer);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineTypeEnum, Integer> getAllTypeFromProducerWithCounts(Producer producer) {
        Map<WineTypeEnum, Integer> result = new HashMap<WineTypeEnum, Integer>();
        int position = 0;
        for (int i = 0; i < producers.length; i++) {
            if (producers[i].equals(producer.getName())) {
                position = i;
                break;
            }
        }
        for (int i = 0; i < types[position].length; i++) {
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(types[position][i], 23 % a);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Integer> getAllColorFromProducerAndTypeWithCounts(Producer producer,
            WineTypeEnum type) {
        Map<WineColorEnum, Integer> result = new HashMap<WineColorEnum, Integer>();
        int position = 0;
        for (int i = 0; i < producers.length; i++) {
            if (producers[i].equals(producer.getName())) {
                position = i;
                break;
            }
        }
        int position2 = 0;
        for (int i = 0; i < types[position].length; i++) {
            if (types[position][i] == type) {
                position2 = i;
                break;
            }
        }
        for (int i = 0; i < colors[position][position2].length; i++) {
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(colors[position][position2][i], 23 % a);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Format, Integer> getAllFormatWithCounts() {
        Map<Format, Integer> result = new HashMap<Format, Integer>();
        String[] keys = formats.keySet().toArray(new String[formats.size()]);
        for (int i = 0; i < formats.size(); i++) {
            Format format = new Format();
            format.setName(keys[i]);
            format.setCapacity(formats.get(keys[i]));
            new DirectPropertyAccessor().getSetter(Format.class, "id").set(format, i, null);
            int a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(format, 23 % a);
        }
        return result;
    }

}
