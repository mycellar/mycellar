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
package fr.peralta.mycellar.interfaces.client.web.components.wine.cloud;

import org.apache.wicket.Component;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.TagCloudPanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.AppellationEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class AppellationComplexTagCloud extends ComplexTagCloud<Appellation> {

    private static final long serialVersionUID = 201108062231L;

    private static final String REGION_COMPONENT_ID = "region";

    private IModel<Region> regionModel;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     */
    public AppellationComplexTagCloud(String id, IModel<String> label) {
        super(id, label);
        add(new RegionComplexTagCloud(REGION_COMPONENT_ID, new StringResourceModel("region", this,
                null)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalConfigureComponent(Appellation modelObject) {
        super.internalConfigureComponent(modelObject);
        if (modelObject == null) {
            setModelObject(createObject());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createComponentForCreation(String id) {
        return new AppellationEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Appellation createObject() {
        Appellation appellation = new Appellation();
        if (regionModel != null) {
            appellation.setRegion(regionModel.getObject());
        }
        return appellation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TagCloudPanel<Appellation> createTagCloudPanel(String id) {
        return new TagCloudPanel<Appellation>(id,
                getListFrom(wineServiceFacade.getAppellationsWithCounts(regionModel.getObject())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return ((RegionComplexTagCloud) get(REGION_COMPONENT_ID)).isValued();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSelectorLabelFor(Appellation object) {
        return object.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getValueLabelFor(Appellation object) {
        return object.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void detachModel() {
        if (regionModel != null) {
            regionModel.detach();
        }
        super.detachModel();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onModelChanged(IEventSource source, Action action) {
        if (source instanceof RegionComplexTagCloud) {
            RegionComplexTagCloud regionComplexTagCloud = (RegionComplexTagCloud) source;
            regionModel = (IModel<Region>) regionComplexTagCloud.getDefaultModel();
            setDefaultModelObject(createObject());
            if (regionComplexTagCloud.isValued() && (regionModel != null)
                    && (regionModel.getObject() != null)
                    && (regionModel.getObject().getId() == null)) {
                send(this, Broadcast.EXACT, Action.ADD);
            }
        } else {
            super.onModelChanged(source, action);
        }
    }
}