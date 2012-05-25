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
package fr.peralta.mycellar.interfaces.client.web.descriptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.wicket.Localizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavDescriptor;
import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavHeaderDescriptor;
import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavPageDescriptor;
import fr.peralta.mycellar.interfaces.client.web.descriptors.entities.shared.IEntityDescriptor;
import fr.peralta.mycellar.interfaces.client.web.descriptors.menu.shared.IMenuDescriptor;
import fr.peralta.mycellar.interfaces.client.web.descriptors.shared.IDescriptor;

/**
 * @author speralta
 */
@Service
public class DescriptorServiceFacade {

    private static final Logger logger = LoggerFactory.getLogger(DescriptorServiceFacade.class);

    private final List<IDescriptor> descriptors = new ArrayList<IDescriptor>();

    private final SortedMap<String, NavHeaderDescriptor> listHeaders = new TreeMap<String, NavHeaderDescriptor>();
    private final Map<NavHeaderDescriptor, SortedMap<String, NavPageDescriptor>> listPages = new HashMap<NavHeaderDescriptor, SortedMap<String, NavPageDescriptor>>();

    private final SortedMap<Integer, NavDescriptor> menuPages = new TreeMap<Integer, NavDescriptor>();

    /**
     * @return the listPages
     */
    public List<NavDescriptor> getListPages() {
        List<NavDescriptor> descriptors = new ArrayList<NavDescriptor>();

        for (NavHeaderDescriptor header : listHeaders.values()) {
            descriptors.add(header);
            descriptors.addAll(listPages.get(header).values());
        }

        return descriptors;
    }

    /**
     * @return the menuPages
     */
    public List<NavDescriptor> getMenuPages() {
        return new ArrayList<NavDescriptor>(menuPages.values());
    }

    public void registerDescriptor(IDescriptor descriptor) {
        if (descriptors.contains(descriptor)) {
            logger.warn("Descriptor already registered {}.", descriptor.getClass().getName());
        } else {
            logger.trace("Register descriptor {}.", descriptor.getClass().getName());
            descriptors.add(descriptor);
            if (descriptor instanceof IEntityDescriptor) {
                IEntityDescriptor<?, ?> entityDescriptor = ((IEntityDescriptor<?, ?>) descriptor);
                NavHeaderDescriptor header = listHeaders.get(Localizer.get().getString(
                        entityDescriptor.getHeaderKey(), null));
                SortedMap<String, NavPageDescriptor> pages;
                if (header == null) {
                    header = new NavHeaderDescriptor(entityDescriptor.getHeaderKey());
                    listHeaders.put(Localizer.get().getString(header.getHeaderKey(), null), header);
                    pages = new TreeMap<String, NavPageDescriptor>();
                    listPages.put(header, pages);
                } else {
                    pages = listPages.get(header);
                }

                pages.put(
                        Localizer.get().getString(entityDescriptor.getTitleKey(), null),
                        new NavPageDescriptor(entityDescriptor.getListPageClass(), entityDescriptor
                                .getTitleKey()));
            }
            if (descriptor instanceof IMenuDescriptor) {
                IMenuDescriptor<?> menuDescriptor = ((IMenuDescriptor<?>) descriptor);
                if (menuDescriptor.getParentKey() != null) {
                    NavHeaderDescriptor header = getHeader(menuDescriptor.getParentKey());
                    if (header == null) {
                        header = new NavHeaderDescriptor(menuDescriptor.getParentKey());
                        menuPages.put(menuDescriptor.getWeight(), header);
                    }
                    header.addPage(
                            menuDescriptor.getWeight(),
                            new NavPageDescriptor(menuDescriptor.getMenuableClass(), menuDescriptor
                                    .getTitleKey()));
                } else {
                    menuPages.put(
                            menuDescriptor.getWeight(),
                            new NavPageDescriptor(menuDescriptor.getMenuableClass(), menuDescriptor
                                    .getTitleKey()));
                }
            }
        }
    }

    /**
     * @param parentKey
     * @return
     */
    private NavHeaderDescriptor getHeader(String parentKey) {
        for (NavDescriptor navDescriptor : menuPages.values()) {
            if (navDescriptor instanceof NavHeaderDescriptor) {
                NavHeaderDescriptor header = (NavHeaderDescriptor) navDescriptor;
                if (header.getHeaderKey().equals(parentKey)) {
                    return header;
                }
            }
        }
        return null;
    }
}
