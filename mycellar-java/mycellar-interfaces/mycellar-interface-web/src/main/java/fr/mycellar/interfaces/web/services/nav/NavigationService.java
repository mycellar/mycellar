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
package fr.mycellar.interfaces.web.services.nav;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mycellar.interfaces.web.descriptors.DescriptorServiceFacade;
import fr.mycellar.interfaces.web.descriptors.menu.shared.IMenuDescriptor;
import fr.mycellar.interfaces.web.descriptors.shared.IDescriptor;

/**
 * @author speralta
 */
@Service
@Path("/navigation")
public class NavigationService {

    private DescriptorServiceFacade descriptorServiceFacade;

    private List<NavDescriptor> menu;

    @PostConstruct
    public void build() {
        List<IDescriptor> descriptors = descriptorServiceFacade.getDescriptors();

        SortedMap<Integer, NavDescriptor> menuPages = new TreeMap<Integer, NavDescriptor>();

        for (IDescriptor descriptor : descriptors) {
            if (descriptor instanceof IMenuDescriptor) {
                IMenuDescriptor menuDescriptor = ((IMenuDescriptor) descriptor);
                if (menuDescriptor.getParentKey() != null) {
                    NavHeaderDescriptor header = getHeader(menuDescriptor.getParentKey(), menuPages);
                    if (header == null) {
                        header = new NavHeaderDescriptor(menuDescriptor.getParentKey(),
                                menuDescriptor.getIcon());
                        menuPages.put(menuDescriptor.getWeight(), header);
                    }
                    header.addPage(
                            menuDescriptor.getWeight(),
                            new NavPageDescriptor(menuDescriptor.getRoute(), menuDescriptor
                                    .getTitleKey(), menuDescriptor.getIcon()));
                } else {
                    menuPages.put(
                            menuDescriptor.getWeight(),
                            new NavPageDescriptor(menuDescriptor.getRoute(), menuDescriptor
                                    .getTitleKey(), menuDescriptor.getIcon()));
                }
            }
        }

        menu = new ArrayList<NavDescriptor>(menuPages.values());
    }

    /**
     * @param parentKey
     * @param menuPages
     * @return
     */
    private NavHeaderDescriptor getHeader(String parentKey,
            SortedMap<Integer, NavDescriptor> menuPages) {
        for (NavDescriptor navDescriptor : menuPages.values()) {
            if (navDescriptor instanceof NavHeaderDescriptor) {
                NavHeaderDescriptor header = (NavHeaderDescriptor) navDescriptor;
                if (header.getLabel().equals(parentKey)) {
                    return header;
                }
            }
        }
        return null;
    }

    /**
     * @return the menu
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("menu")
    public List<NavDescriptor> getMenu() {
        return menu;
    }

    /**
     * @param descriptorServiceFacade
     *            the descriptorServiceFacade to set
     */
    @Autowired
    public void setDescriptorServiceFacade(DescriptorServiceFacade descriptorServiceFacade) {
        this.descriptorServiceFacade = descriptorServiceFacade;
    }

}
