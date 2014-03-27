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
package fr.mycellar.interfaces.facades.admin;

import java.util.List;

import fr.mycellar.domain.admin.Configuration;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
public interface AdministrationServiceFacade {

    long countConfigurations(SearchParameters<Configuration> searchParameters);

    Configuration getConfigurationById(Integer configurationId);

    List<Configuration> getConfigurations(SearchParameters<Configuration> searchParameters);

    Configuration saveConfiguration(Configuration configuration) throws BusinessException;

}
