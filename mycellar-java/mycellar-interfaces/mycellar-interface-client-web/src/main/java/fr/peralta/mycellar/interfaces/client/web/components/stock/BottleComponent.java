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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.WineComplexAutocomplete;
import fr.peralta.mycellar.interfaces.client.web.components.wine.cloud.FormatComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;

/**
 * @author speralta
 */
public class BottleComponent extends Panel {

    private static final long serialVersionUID = 201109081922L;

    private static final String WINE_COMPONENT_ID = "wine";
    private static final String FORMAT_COMPONENT_ID = "format";

    private final Logger logger = LoggerFactory.getLogger(BottleComponent.class);

    /**
     * @param id
     */
    public BottleComponent(String id) {
        super(id);
        add(new WineComplexAutocomplete(WINE_COMPONENT_ID, new StringResourceModel("wine", this,
                null)));
        add(new FormatComplexTagCloud(FORMAT_COMPONENT_ID, new StringResourceModel("format", this,
                null)));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected IModel<?> initModel() {
        return new CompoundPropertyModel<Bottle>((IModel<Bottle>) super.initModel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        if (getDefaultModelObject() == null) {
            setDefaultModelObject(new Bottle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:

                break;
            case CANCEL:

                break;
            case ADD:

                break;
            case SAVE:

                break;
            case MODEL_CHANGED:

                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            if (action.isAjax()) {
                action.getAjaxRequestTarget().add(this);
            }
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

}
