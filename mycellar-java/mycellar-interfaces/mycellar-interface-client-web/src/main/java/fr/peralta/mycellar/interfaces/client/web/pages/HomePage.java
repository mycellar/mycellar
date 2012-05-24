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
package fr.peralta.mycellar.interfaces.client.web.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.pages.booking.BookingEventsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.pedia.PediaHomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.HomeSuperPage;
import fr.peralta.mycellar.interfaces.client.web.security.UserKey;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class HomePage extends HomeSuperPage {

    private static final long serialVersionUID = 201117181723L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private Label connect;

    /**
     * @param parameters
     */
    public HomePage(PageParameters parameters) {
        super(parameters);
        add((connect = new Label("connect", new StringResourceModel("connect", null)))
                .setOutputMarkupId(true).setVisibilityAllowed(false));
        add(new AjaxLink<Void>("bookingEvents") {
            private static final long serialVersionUID = 201205240902L;

            /**
             * {@inheritDoc}
             */
            @Override
            public void onClick(AjaxRequestTarget target) {
                if (UserKey.getUserLoggedIn() != null) {
                    setResponsePage(BookingEventsPage.class);
                } else {
                    connect.setVisibilityAllowed(true);
                    target.add(HomePage.this);
                }
            }

        });
        add(new BookmarkablePageLink<Void>("newUser", NewUserPage.class));
        add(new BookmarkablePageLink<Void>("vinopedia", PediaHomePage.class));
        add(new Label("vinopediaWithCount", new StringResourceModel("vinopediaText", null,
                new Object[] { wineServiceFacade.countWines(new SearchForm()) })));
    }

}
