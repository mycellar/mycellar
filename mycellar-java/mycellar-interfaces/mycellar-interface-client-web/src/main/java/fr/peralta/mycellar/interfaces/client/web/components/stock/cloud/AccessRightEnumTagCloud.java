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
package fr.peralta.mycellar.interfaces.client.web.components.stock.cloud;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.stock.AccessRightEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.SimpleTagCloud;

/**
 * @author speralta
 */
public class AccessRightEnumTagCloud extends SimpleTagCloud<AccessRightEnum> {

    private static final long serialVersionUID = 201111161904L;

    /**
     * @param id
     * @param label
     */
    public AccessRightEnumTagCloud(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<AccessRightEnum, Long> getChoices() {
        Map<AccessRightEnum, Long> accessRightEnums = new HashMap<AccessRightEnum, Long>();
        accessRightEnums.put(AccessRightEnum.MODIFY, 1L);
        accessRightEnums.put(AccessRightEnum.READ, 1L);
        return accessRightEnums;
    }

}
