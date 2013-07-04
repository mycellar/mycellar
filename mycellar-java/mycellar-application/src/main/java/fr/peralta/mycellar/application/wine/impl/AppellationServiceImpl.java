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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.wine.AppellationService;
import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Appellation_;
import fr.peralta.mycellar.domain.wine.Region_;
import fr.peralta.mycellar.domain.wine.repository.AppellationRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class AppellationServiceImpl extends
        AbstractSimpleService<Appellation, AppellationRepository> implements AppellationService {

    private AppellationRepository appellationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Appellation entity) throws BusinessException {
        Appellation model = new Appellation();
        model.setRegion(entity.getRegion());
        model.setName(entity.getName());
        Appellation existing = appellationRepository
                .findUniqueOrNone(new SearchParameters() //
                        .property(
                                PropertySelector.newPropertySelector(entity.getRegion().getId(),
                                        Appellation_.region, Region_.id)) //
                        .property(
                                PropertySelector.newPropertySelector(entity.getName(),
                                        NamedEntity_.name)) //
                );
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.APPELLATION_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AppellationRepository getRepository() {
        return appellationRepository;
    }

    /**
     * @param appellationRepository
     *            the appellationRepository to set
     */
    @Inject
    public void setAppellationRepository(AppellationRepository appellationRepository) {
        this.appellationRepository = appellationRepository;
    }

}
