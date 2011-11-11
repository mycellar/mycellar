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
package fr.peralta.mycellar.interfaces.client.web.pages.admin;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
public class StackPage extends AdminSuperPage {

    private static final long serialVersionUID = 201111092326L;

    public static PageParameters getPageParameters(Integer stackId) {
        return new PageParameters().add("stackId", stackId);
    }

    @SpringBean
    private StackServiceFacade stackServiceFacade;

    /**
     * @param parameters
     */
    public StackPage(PageParameters parameters) {
        super(parameters);
        Integer stackId = parameters.get("stackId").toInteger();
        setDefaultModel(new CompoundPropertyModel<Stack>(stackServiceFacade.getStackById(stackId)));
        add(new MultiLineLabel("stack"));
    }

}
