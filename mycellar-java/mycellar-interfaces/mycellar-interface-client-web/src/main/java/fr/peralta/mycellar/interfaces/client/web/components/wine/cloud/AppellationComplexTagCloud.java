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

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Appellation;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.AppellationForm;
import fr.peralta.mycellar.interfaces.client.web.shared.FilterEnumHelper;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class AppellationComplexTagCloud extends ComplexTagCloud<Appellation> {

    private static final long serialVersionUID = 201111161904L;

    private static final String REGION_COMPONENT_ID = "region";

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final RegionComplexTagCloud regionComplexTagCloud;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public AppellationComplexTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count, FilterEnum... filters) {
        super(id, label, searchFormModel, count, filters);
        add(regionComplexTagCloud = new RegionComplexTagCloud(REGION_COMPONENT_ID,
                new StringResourceModel("region", this, null), searchFormModel, count,
                FilterEnumHelper.removeFilter(filters, FilterEnum.APPELLATION)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setOtherComponentsVisibilityAllowed(boolean allowed) {
        regionComplexTagCloud.setVisibilityAllowed(allowed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Appellation> createForm(String id, IModel<SearchForm> searchFormModel) {
        return new AppellationForm(id, searchFormModel, getCount(), getFilters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Appellation createObject() {
        Appellation appellation = new Appellation();
        appellation.setRegion(regionComplexTagCloud.getModelObject());
        return appellation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Appellation, Long> getChoices(SearchForm searchForm, CountEnum count,
            FilterEnum[] filters) {
        return wineServiceFacade.getAppellations(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return regionComplexTagCloud.isValued();
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
    protected void onModelChanged(IEvent<?> event) {
        getSearchFormModel().getObject().replaceSet(FilterEnum.REGION,
                regionComplexTagCloud.getModelObject());
        if (!regionComplexTagCloud.isValued()) {
            markAsNonValued();
        } else {
            Region region = regionComplexTagCloud.getModelObject();
            if ((region != null) && (region.getId() == null)) {
                setDefaultModelObject(createObject());
                send(this, Broadcast.EXACT, Action.ADD);
            }
        }
        AjaxTool.ajaxReRender(this);
        event.stop();
    }

}