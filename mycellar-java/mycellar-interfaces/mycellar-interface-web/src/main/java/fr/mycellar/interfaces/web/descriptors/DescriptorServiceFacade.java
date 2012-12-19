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
package fr.mycellar.interfaces.web.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.web.descriptors.shared.IDescriptor;

/**
 * @author speralta
 */
@Service
public class DescriptorServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(DescriptorServiceFacade.class);

    private final List<IDescriptor> descriptors = new ArrayList<IDescriptor>();

    /**
     * @return the descriptors
     */
    public List<IDescriptor> getDescriptors() {
        return descriptors;
    }

    public void registerDescriptor(IDescriptor descriptor) {
        if (descriptors.contains(descriptor)) {
            logger.warn("Descriptor already registered {}.", descriptor.getClass().getName());
        } else {
            logger.trace("Register descriptor {}.", descriptor.getClass().getName());
            descriptors.add(descriptor);
        }
    }

}
