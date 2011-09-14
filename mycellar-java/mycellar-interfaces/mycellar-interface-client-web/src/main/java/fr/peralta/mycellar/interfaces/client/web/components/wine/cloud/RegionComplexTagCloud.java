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

import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.TagCloudPanel;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.RegionEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class RegionComplexTagCloud extends ComplexTagCloud<Region> {

    private static final long serialVersionUID = 201107252130L;

    private static final String COUNTRY_COMPONENT_ID = "country";

    private IModel<Country> countryModel;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     */
    public RegionComplexTagCloud(String id, IModel<String> label) {
        super(id, label);
        add(new CountryComplexTagCloud(COUNTRY_COMPONENT_ID, new StringResourceModel("country",
                this, null)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalConfigureComponent(Region modelObject) {
        super.internalConfigureComponent(modelObject);
        if (modelObject == null) {
            setModelObject(createObject());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TagCloudPanel<Region> createTagCloudPanel(String id) {
        return new TagCloudPanel<Region>(id,
                getListFrom(wineServiceFacade.getRegionsWithCounts(countryModel.getObject())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return ((CountryComplexTagCloud) get(COUNTRY_COMPONENT_ID)).isValued();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getSelectorLabelFor(Region object) {
        return object.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getValueLabelFor(Region object) {
        return object.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createComponentForCreation(String id) {
        return new RegionEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Region createObject() {
        Region region = new Region();
        if (countryModel != null) {
            region.setCountry(countryModel.getObject());
        }
        return region;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void detachModel() {
        if (countryModel != null) {
            countryModel.detach();
        }
        super.detachModel();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onModelChanged(IEventSource source, Action action) {
        if (source instanceof CountryComplexTagCloud) {
            CountryComplexTagCloud countryComplexTagCloud = (CountryComplexTagCloud) source;
            countryModel = (IModel<Country>) countryComplexTagCloud.getDefaultModel();
            setDefaultModelObject(createObject());
            if (countryComplexTagCloud.isValued() && (countryModel != null)
                    && (countryModel.getObject() != null)
                    && (countryModel.getObject().getId() == null)) {
                send(this, Broadcast.EXACT, Action.ADD);
            }
        } else {
            super.onModelChanged(source, action);
        }
    }

}