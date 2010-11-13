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
package fr.peralta.mycellar.interfaces.facades.shared.mappers;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import fr.peralta.mycellar.interfaces.facades.shared.MapperServiceFacade;

/**
 * @author speralta
 */
public abstract class AbstractMapper<FROM, TO> implements IMapper<FROM, TO> {

    private MapperServiceFacade mapperServiceFacade;

    @PostConstruct
    public void initialize() {
        mapperServiceFacade.registerMapper(this, getFromClass(), getToClass());
    }

    protected abstract Class<FROM> getFromClass();

    protected abstract Class<TO> getToClass();

    /**
     * @return the mapperServiceFacade
     */
    protected final MapperServiceFacade getMapperServiceFacade() {
        return mapperServiceFacade;
    }

    /**
     * @param mapperServiceFacade
     *            the mapperServiceFacade to set
     */
    @Resource
    public final void setMapperServiceFacade(MapperServiceFacade mapperServiceFacade) {
        this.mapperServiceFacade = mapperServiceFacade;
    }

}
