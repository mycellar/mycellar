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

import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Region;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.SimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.shared.FilterEnumHelper;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class RegionSimpleTagCloud extends SimpleTagCloud<Region> {

    private static final long serialVersionUID = 201111161904L;

    private static final String COUNTRY_COMPONENT_ID = "country";

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final CountrySimpleTagCloud countrySimpleTagCloud;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public RegionSimpleTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count, FilterEnum... filters) {
        super(id, label, searchFormModel, count, filters);
        add(countrySimpleTagCloud = new CountrySimpleTagCloud(COUNTRY_COMPONENT_ID,
                new StringResourceModel("country", this, null), searchFormModel, count,
                FilterEnumHelper.removeFilter(filters, FilterEnum.REGION)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<Region, Long> getChoices(SearchForm searchForm, CountEnum count,
            FilterEnum... filters) {
        return wineServiceFacade.getRegions(searchForm, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return countrySimpleTagCloud.isValued();
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
    protected String getSelectorLabelFor(Region object) {
        return object.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onModelChanged(IEvent<?> event) {
        getSearchFormModel().getObject().replaceSet(FilterEnum.COUNTRY,
                countrySimpleTagCloud.getModelObject());
        if (!countrySimpleTagCloud.isValued()) {
            markAsNonValued();
        }
        initializeIfUnique();
        AjaxTool.ajaxReRender(this);
        event.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Region createDefaultObject() {
        Region region = new Region();
        region.setCountry(countrySimpleTagCloud.getModelObject());
        return region;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToReplace() {
        return FilterEnum.REGION;
    }
}