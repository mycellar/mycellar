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

    private static final Map<String, Float> sampleFormats = new HashMap<String, Float>();
    private static final String[] sampleCountries = new String[] { "France", "Espagne", "Italie",
            "Hongrie", "Portugal", "Allemagne", "Etats-unis", "Afrique du Sud", "Argentine",
            "Belgique" };
    private static final String[][] sampleRegions = new String[][] {
            new String[] { "Bourgogne", "Bordeaux" }, new String[] { "Espagne" },
            new String[] { "Italie" }, new String[] { "Hongrie" }, new String[] { "Portugal" },
            new String[] { "Allemagne" }, new String[] { "Etats-unis" },
            new String[] { "Afrique du Sud" }, new String[] { "Argentine" },
            new String[] { "Belgique" } };
    private static final String[][] sampleAppellations = new String[][] {
            new String[] { "Chambertin", "Meursault" }, new String[] { "Pomerol" },
            new String[] { "Italie" }, new String[] { "Hongrie" }, new String[] { "Portugal" },
            new String[] { "Allemagne" }, new String[] { "Etats-unis" },
            new String[] { "Afrique du Sud" }, new String[] { "Argentine" },
            new String[] { "Belgique" } };
    private static final String[] sampleProducers = new String[] { "Domaine Rousseau",
            "Domaine Macle", "Château Margaux", "Château Cheval Blanc", "Clos des papes",
            "Clos des lambrays", "Clos de tart" };
    private static final WineTypeEnum[][] sampleTypes = new WineTypeEnum[][] {
            new WineTypeEnum[] { WineTypeEnum.STILL },
            new WineTypeEnum[] { WineTypeEnum.STILL, WineTypeEnum.SPARKLING },
            new WineTypeEnum[] { WineTypeEnum.STILL }, new WineTypeEnum[] { WineTypeEnum.STILL },
            new WineTypeEnum[] { WineTypeEnum.STILL }, new WineTypeEnum[] { WineTypeEnum.STILL },
            new WineTypeEnum[] { WineTypeEnum.STILL } };
    private static final WineColorEnum[][][] sampleColors = new WineColorEnum[][][] {
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.WHITE, WineColorEnum.RED } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.RED, WineColorEnum.WHITE },
                    new WineColorEnum[] { WineColorEnum.WHITE } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.ROSE, WineColorEnum.RED } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.OTHER, WineColorEnum.RED } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.RED },
                    new WineColorEnum[] { WineColorEnum.OTHER } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.WHITE, WineColorEnum.OTHER } },
            new WineColorEnum[][] { new WineColorEnum[] { WineColorEnum.RED, WineColorEnum.WHITE,
                    WineColorEnum.OTHER } } };
    static {
        sampleFormats.put("Demi-bouteille", 0.375f);
        sampleFormats.put("Bouteille", 0.75f);
        sampleFormats.put("Magnum", 1.5f);
        sampleFormats.put("Double-magnum", 3f);
        sampleFormats.put("Clavelin", 0.62f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Country, Long> getAllCountriesWithCounts() {
        Map<Country, Long> result = new HashMap<Country, Long>();
        for (int i = 0; i < sampleCountries.length; i++) {
            Country country = new Country();
            country.setName(sampleCountries[i]);
            new DirectPropertyAccessor().getSetter(Country.class, "id").set(country, i, null);
            long a = (i + 5) % 10;
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
    public Map<Region, Long> getAllRegionsFromCountriesWithCounts(Country... countries) {
        Map<Region, Long> result = new HashMap<Region, Long>();
        if ((countries != null) && (countries.length > 0)) {
            for (Country country : countries) {
                int position = 0;
                for (int i = 0; i < sampleCountries.length; i++) {
                    if (sampleCountries[i].equals(country.getName())) {
                        position = i;
                        break;
                    }
                }
                for (int i = 0; i < sampleRegions[position].length; i++) {
                    Region region = new Region();
                    region.setName(sampleRegions[position][i]);
                    region.setCountry(country);
                    new DirectPropertyAccessor().getSetter(Region.class, "id").set(region, i, null);
                    long a = (i + 5) % 10;
                    if (a == 0) {
                        a = 2;
                    }
                    result.put(region, 23 % a);
                }
            }
        } else {
            for (int position = 0; position < sampleRegions.length; position++) {
                Country country = new Country();
                country.setName(sampleCountries[position]);
                new DirectPropertyAccessor().getSetter(Country.class, "id").set(country, position,
                        null);
                long a = (position + 5) % 10;
                if (a == 0) {
                    a = 2;
                }
                for (int i = 0; i < sampleRegions[position].length; i++) {
                    Region region = new Region();
                    region.setName(sampleRegions[position][i]);
                    region.setCountry(country);
                    new DirectPropertyAccessor().getSetter(Region.class, "id").set(region, i, null);
                    long b = (i + 5) % 10;
                    if (b == 0) {
                        b = 2;
                    }
                    result.put(region, 23 % b);
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Appellation, Long> getAllAppellationsFromRegionsWithCounts(Region... regions) {
        Map<Appellation, Long> result = new HashMap<Appellation, Long>();
        if ((regions != null) && (regions.length > 0)) {
            for (Region region : regions) {
                int position = 0;
                for (int i = 0; i < sampleRegions.length; i++) {
                    for (int j = 0; j < sampleRegions[i].length; j++) {
                        if (sampleRegions[i][j].equals(region.getName())) {
                            position = i + j;
                            break;
                        }
                    }
                }
                for (int i = 0; i < sampleAppellations[position].length; i++) {
                    Appellation appellation = new Appellation();
                    appellation.setName(sampleAppellations[position][i]);
                    appellation.setRegion(region);
                    new DirectPropertyAccessor().getSetter(Appellation.class, "id").set(
                            appellation, i, null);
                    long a = (i + 5) % 10;
                    if (a == 0) {
                        a = 2;
                    }
                    result.put(appellation, 23 % a);
                }
            }
        } else {
            for (int position = 0; position < sampleRegions.length; position++) {
                Country country = new Country();
                country.setName(sampleCountries[position]);
                new DirectPropertyAccessor().getSetter(Country.class, "id").set(country, position,
                        null);
                for (int i = 0; i < sampleRegions[position].length; i++) {
                    Region region = new Region();
                    region.setName(sampleRegions[position][i]);
                    region.setCountry(country);
                    new DirectPropertyAccessor().getSetter(Region.class, "id").set(region, i, null);
                    for (int j = 0; j < sampleAppellations[position].length; j++) {
                        Appellation appellation = new Appellation();
                        appellation.setName(sampleAppellations[position][j]);
                        appellation.setRegion(region);
                        new DirectPropertyAccessor().getSetter(Appellation.class, "id").set(
                                appellation, j, null);
                        long a = (j + 5) % 10;
                        if (a == 0) {
                            a = 2;
                        }
                        result.put(appellation, 23 % a);
                    }
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Producer> getAllProducersLike(String term) {
        List<Producer> result = new ArrayList<Producer>();
        int i = 1;
        for (String producerName : sampleProducers) {
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
    public Map<WineTypeEnum, Long> getAllTypeFromProducerWithCounts(Producer producer) {
        Map<WineTypeEnum, Long> result = new HashMap<WineTypeEnum, Long>();
        int position = 0;
        for (int i = 0; i < sampleProducers.length; i++) {
            if (sampleProducers[i].equals(producer.getName())) {
                position = i;
                break;
            }
        }
        for (int i = 0; i < sampleTypes[position].length; i++) {
            long a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(sampleTypes[position][i], 23 % a);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<WineColorEnum, Long> getAllColorFromProducerAndTypeWithCounts(Producer producer,
            WineTypeEnum type) {
        Map<WineColorEnum, Long> result = new HashMap<WineColorEnum, Long>();
        int position = 0;
        for (int i = 0; i < sampleProducers.length; i++) {
            if (sampleProducers[i].equals(producer.getName())) {
                position = i;
                break;
            }
        }
        int position2 = 0;
        for (int i = 0; i < sampleTypes[position].length; i++) {
            if (sampleTypes[position][i] == type) {
                position2 = i;
                break;
            }
        }
        for (int i = 0; i < sampleColors[position][position2].length; i++) {
            long a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(sampleColors[position][position2][i], 23 % a);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Format, Long> getAllFormatWithCounts() {
        Map<Format, Long> result = new HashMap<Format, Long>();
        String[] keys = sampleFormats.keySet().toArray(new String[sampleFormats.size()]);
        for (int i = 0; i < sampleFormats.size(); i++) {
            Format format = new Format();
            format.setName(keys[i]);
            format.setCapacity(sampleFormats.get(keys[i]));
            new DirectPropertyAccessor().getSetter(Format.class, "id").set(format, i, null);
            long a = (i + 5) % 10;
            if (a == 0) {
                a = 2;
            }
            result.put(format, 23 % a);
        }
        return result;
    }

}
