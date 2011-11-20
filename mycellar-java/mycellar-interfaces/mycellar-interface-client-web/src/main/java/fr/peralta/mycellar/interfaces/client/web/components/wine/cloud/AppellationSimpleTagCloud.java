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

import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.SimpleTagCloud;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class AppellationSimpleTagCloud extends SimpleTagCloud<Appellation> {

    private static final long serialVersionUID = 201111161904L;

    private static final String REGION_COMPONENT_ID = "region";

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
    public AppellationSimpleTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count) {
        super(id, label);
        this.count = count;
        this.searchFormModel = searchFormModel;
        add(new RegionSimpleTagCloud(REGION_COMPONENT_ID, new StringResourceModel("region", this,
                null), searchFormModel, count));
    }

    @SuppressWarnings("unchecked")
    private IModel<Region> getRegionModel() {
        return (IModel<Region>) get(REGION_COMPONENT_ID).getDefaultModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Appellation, Long> getChoices() {
        SearchForm searchForm = searchFormModel.getObject();
        searchForm.replaceSet(FilterEnum.REGION, getRegionModel().getObject());
        return wineServiceFacade.getAppellations(searchForm, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return ((RegionSimpleTagCloud) get(REGION_COMPONENT_ID)).isValued();
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
    protected String getSelectorLabelFor(Appellation object) {
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
        refreshTagCloud();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Appellation createDefaultObject() {
        return new Appellation();
    }

}