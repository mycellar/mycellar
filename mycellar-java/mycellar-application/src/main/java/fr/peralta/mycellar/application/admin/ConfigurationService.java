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
package fr.peralta.mycellar.application.admin;

import fr.peralta.mycellar.application.shared.EntityService;
import fr.peralta.mycellar.domain.admin.Configuration;
import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrder;
import fr.peralta.mycellar.domain.admin.repository.ConfigurationOrderEnum;

/**
 * @author speralta
 */
public interface ConfigurationService extends
        EntityService<Configuration, ConfigurationOrderEnum, ConfigurationOrder> {

    /**
     * @param key
     * @return
     */
    Configuration find(ConfigurationKeyEnum key);

    /**
     * @return
     */
    String[] getReminderAddressReceivers();

    /**
     * @return
     */
    String getMailAddressSender();

}
