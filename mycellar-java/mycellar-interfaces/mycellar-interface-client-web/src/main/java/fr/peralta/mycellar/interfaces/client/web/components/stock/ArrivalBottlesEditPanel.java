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
package fr.peralta.mycellar.interfaces.client.web.components.stock;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.Panel;

import fr.peralta.mycellar.domain.stock.ArrivalBottle;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.ArrivalBottleEditPanel;

/**
 * @author speralta
 */
public class ArrivalBottlesEditPanel extends Panel {

    private static final long serialVersionUID = 201011071626L;

    private static final String ARRIVAL_BOTTLE_COMPONENT_ID = "arrivalBottle";

    /**
     * @param id
     */
    public ArrivalBottlesEditPanel(String id) {
        super(id);
        ArrivalBottlesView bottlesView = new ArrivalBottlesView("arrivalBottles");
        add(bottlesView);
        add(new ActionLink("addBottle", Action.ADD));
        add(createHiddenBottleForm());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case ADD:
                displayBottleForm();
                break;
            case SAVE:
                ((List<ArrivalBottle>) getDefaultModelObject()).add((ArrivalBottle) get(
                        ARRIVAL_BOTTLE_COMPONENT_ID).getDefaultModelObject());
                replace(createHiddenBottleForm());
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
        }
    }

    /**
     * @return
     */
    private Component createHiddenBottleForm() {
        return new ObjectForm<ArrivalBottle>(ARRIVAL_BOTTLE_COMPONENT_ID, new ArrivalBottle())
                .replace(
                        new ArrivalBottleEditPanel(ObjectForm.EDIT_PANEL_COMPONENT_ID)
                                .setOutputMarkupId(true)).setVisibilityAllowed(false);
    }

    /**
     * @return
     */
    private Component displayBottleForm() {
        return get(ARRIVAL_BOTTLE_COMPONENT_ID).setVisibilityAllowed(true);
    }
}
