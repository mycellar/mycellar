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
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.SimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.TagCloudPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class RegionSimpleTagCloud extends SimpleTagCloud<Region> {

    private static final long serialVersionUID = 201107252130L;

    private static final String COUNTRY_COMPONENT_ID = "country";

    private final CountEnum count;

    private final IModel<SearchForm> searchFormModel;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     */
    public RegionSimpleTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count) {
        super(id, label);
        this.count = count;
        this.searchFormModel = searchFormModel;
        add(new CountrySimpleTagCloud(COUNTRY_COMPONENT_ID, new StringResourceModel("country",
                this, null), searchFormModel, count));
    }

    @SuppressWarnings("unchecked")
    private IModel<Country> getCountryModel() {
        return (IModel<Country>) get(COUNTRY_COMPONENT_ID).getDefaultModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void internalConfigureComponent(Region modelObject) {
        super.internalConfigureComponent(modelObject);
        if (modelObject == null) {
            setModelObject(new Region());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TagCloudPanel<Region> createTagCloudPanel(String id) {
        SearchForm searchForm = searchFormModel.getObject();
        searchForm.addToSet(FilterEnum.COUNTRY, getCountryModel().getObject());
        return new TagCloudPanel<Region>(id, getListFrom(wineServiceFacade.getRegions(searchForm,
                count)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return ((CountrySimpleTagCloud) get(COUNTRY_COMPONENT_ID)).isValued();
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
    protected void detachModel() {
        searchFormModel.detach();
        super.detachModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged(IEventSource source, Action action) {
        // Nothing to do
    }

}