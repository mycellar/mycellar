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
package fr.peralta.mycellar.interfaces.client.web.components.stock.cloud;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.ComplexTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.TagCloudPanel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.CellarEditPanel;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarComplexTagCloud extends ComplexTagCloud<Cellar> {

    private static final long serialVersionUID = 201107252130L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param id
     * @param label
     */
    public CellarComplexTagCloud(String id, IModel<String> label) {
        super(id, label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createComponentForCreation(String id) {
        return new CellarEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Cellar createObject() {
        Cellar cellar = new Cellar();
        cellar.setOwner(UserKey.getUserLoggedIn());
        return cellar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TagCloudPanel<Cellar> createTagCloudPanel(String id) {
        return new TagCloudPanel<Cellar>(id,
                getListFrom(stockServiceFacade.getAllCellarsWithCountsFromUser(UserKey
                        .getUserLoggedIn())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return true;
    }

}
