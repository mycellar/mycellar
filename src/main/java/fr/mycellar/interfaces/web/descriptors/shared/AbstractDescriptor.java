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
package fr.mycellar.interfaces.web.descriptors.shared;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import fr.mycellar.interfaces.web.descriptors.DescriptorServiceFacade;

/**
 * @author speralta
 */
public abstract class AbstractDescriptor implements IDescriptor {
    private DescriptorServiceFacade descriptorServiceFacade;

    @PostConstruct
    public final void initialize() {
        descriptorServiceFacade.registerDescriptor(this);
        internalInitialize();
    }

    /**
     * Method to override if you want to do something after injection and before
     * use.<br>
     * Called in {@link #initialize()} annotated with {@link PostConstruct}.
     */
    protected void internalInitialize() {

    }

    /**
     * @return the descriptorServiceFacade
     */
    protected final DescriptorServiceFacade getDescriptorServiceFacade() {
        return descriptorServiceFacade;
    }

    /**
     * @param descriptorServiceFacade
     *            the descriptorServiceFacade to set
     */
    @Resource
    public void setDescriptorServiceFacade(DescriptorServiceFacade descriptorServiceFacade) {
        this.descriptorServiceFacade = descriptorServiceFacade;
    }

}
