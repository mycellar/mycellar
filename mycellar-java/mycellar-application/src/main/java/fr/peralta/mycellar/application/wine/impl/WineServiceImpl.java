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
package fr.peralta.mycellar.application.wine.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.wine.WineService;
import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Appellation_;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.Producer_;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.domain.wine.Wine_;
import fr.peralta.mycellar.domain.wine.repository.WineRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class WineServiceImpl extends AbstractSimpleService<Wine, WineRepository> implements
        WineService {

    private WineRepository wineRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Wine find(Producer producer, Appellation appellation, WineTypeEnum type,
            WineColorEnum color, String name, Integer vintage) {
        Wine model = new Wine();
        model.setProducer(producer);
        model.setAppellation(appellation);
        model.setType(type);
        model.setColor(color);
        model.setName(name);
        model.setVintage(vintage);
        return wineRepository.findUniqueOrNone(new SearchParameters()
                .property(
                        PropertySelector.newPropertySelector(producer.getId(), Wine_.producer,
                                Producer_.id)) //
                .property(
                        PropertySelector.newPropertySelector(appellation.getId(),
                                Wine_.appellation, Appellation_.id)) //
                .property(PropertySelector.newPropertySelector(type, Wine_.type)) //
                .property(PropertySelector.newPropertySelector(color, Wine_.color)) //
                .property(PropertySelector.newPropertySelector(name, NamedEntity_.name)) //
                .property(PropertySelector.newPropertySelector(vintage, Wine_.vintage)) //
                );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Wine entity) throws BusinessException {
        Wine existing = find(entity.getProducer(), entity.getAppellation(), entity.getType(),
                entity.getColor(), entity.getName(), entity.getVintage());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.WINE_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Wine> createVintages(Wine wine, int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("From (" + from + ") must be before to (" + to
                    + ").");
        }
        List<Wine> copies = new ArrayList<Wine>();
        for (int i = from; i <= to; i++) {
            copies.add(createVintage(wine, i));
        }
        return copies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wine createVintage(Wine wine, int year) {
        Wine copy = new Wine();
        copy.setAppellation(wine.getAppellation());
        copy.setColor(wine.getColor());
        copy.setDescription(wine.getDescription());
        copy.setName(wine.getName());
        copy.setProducer(wine.getProducer());
        copy.setRanking(wine.getRanking());
        copy.setType(wine.getType());
        copy.setVintage(year);
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected WineRepository getRepository() {
        return wineRepository;
    }

    /**
     * @param wineRepository
     *            the wineRepository to set
     */
    @Inject
    public void setWineRepository(WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

}
