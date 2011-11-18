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

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Country;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.wine.edit.RegionEditPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class RegionComplexTagCloud extends ComplexTagCloud<Region> {

    private static final long serialVersionUID = 201111161904L;

    private static final String COUNTRY_COMPONENT_ID = "country";

    private final IModel<SearchForm> searchFormModel;

    private final CountEnum count;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     */
    public RegionComplexTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count) {
        super(id, label);
        this.searchFormModel = searchFormModel;
        this.count = count;
        add(new CountryComplexTagCloud(COUNTRY_COMPONENT_ID, new StringResourceModel("country",
                this, null), searchFormModel, count));
    }

    private IModel<? extends Country> getCountryModel() {
        return ((CountryComplexTagCloud) get(COUNTRY_COMPONENT_ID)).getModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Region, Long> getChoices() {
        SearchForm searchForm = searchFormModel.getObject();
        searchForm.replaceSet(FilterEnum.COUNTRY, getCountryModel().getObject());
        return wineServiceFacade.getRegions(searchForm, count);
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
        IModel<? extends Country> countryModel = getCountryModel();
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
        searchFormModel.detach();
        super.detachModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged(IEventSource source, Action action) {
        if (source instanceof CountryComplexTagCloud) {
            refreshTagCloud();
            CountryComplexTagCloud countryComplexTagCloud = (CountryComplexTagCloud) source;
            IModel<? extends Country> countryModel = countryComplexTagCloud.getModel();
            if (countryComplexTagCloud.isValued() && (countryModel != null)
                    && (countryModel.getObject() != null)
                    && (countryModel.getObject().getId() == null)) {
                setDefaultModelObject(createObject());
                send(this, Broadcast.EXACT, Action.ADD);
            }
        } else {
            super.onModelChanged(source, action);
        }
    }

}