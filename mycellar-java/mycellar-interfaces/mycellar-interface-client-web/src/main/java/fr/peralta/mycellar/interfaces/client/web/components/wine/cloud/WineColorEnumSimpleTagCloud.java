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

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.SimpleTagCloud;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class WineColorEnumSimpleTagCloud extends SimpleTagCloud<WineColorEnum> {

    private static final long serialVersionUID = 201111161904L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public WineColorEnumSimpleTagCloud(String id, IModel<String> label,
            IModel<SearchForm> searchFormModel, CountEnum count, FilterEnum... filters) {
        super(id, label, searchFormModel, count, filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<WineColorEnum, Long> getChoices(SearchForm searchForm, CountEnum count,
            FilterEnum... filters) {
        return wineServiceFacade.getColors(searchForm, count);
    }

}
