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
package fr.peralta.mycellar.interfaces.client.web.pages.cellar;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.interfaces.client.web.components.shared.tab.BootstrapAjaxTabbedPanel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.tab.CellarShareTabPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.CellarSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class ShareCellarsPage extends CellarSuperPage {

    /**
     * @author speralta
     */
    public class CellarShareTab extends AbstractTab {

        private static final long serialVersionUID = 201201231830L;

        private final IModel<Cellar> cellarModel;

        /**
         * @param cellar
         * @param cellarSharesModel
         */
        public CellarShareTab(Cellar cellar) {
            super(new Model<String>(cellar.getName()));
            cellarModel = new Model<Cellar>(cellar);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public WebMarkupContainer getPanel(String panelId) {
            return new CellarShareTabPanel(panelId, cellarModel);
        }

    }

    private static final long serialVersionUID = 201112061613L;

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param parameters
     */
    public ShareCellarsPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        List<CellarShareTab> tabs = new ArrayList<CellarShareTab>();
        for (Cellar cellar : stockServiceFacade.getCellars(
                new SearchForm().setCellarModification(true).addToSet(FilterEnum.USER,
                        UserKey.getUserLoggedIn()), CountEnum.STOCK_QUANTITY).keySet()) {
            tabs.add(new CellarShareTab(cellar));
        }
        if (tabs.size() == 0) {
            add(new Label("cellars", new StringResourceModel("noCellar", null)));
        } else {
            add(new BootstrapAjaxTabbedPanel<CellarShareTab>("cellars", tabs));
        }
    }

}
