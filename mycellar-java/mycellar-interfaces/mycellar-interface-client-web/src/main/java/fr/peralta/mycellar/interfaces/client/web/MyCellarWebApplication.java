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
package fr.peralta.mycellar.interfaces.client.web;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.pages.InternalErrorPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;
import org.wicketstuff.security.swarm.SwarmWebApplication;

import fr.peralta.mycellar.domain.stock.AccessRightEnum;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.converters.AccessRightEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.LocalDateConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.WineColorEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.WineTypeEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.pages.HomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AdminPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.ListUsersPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.StackPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.CellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.DrinkBottlesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.InputOutputPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.PackageArrivalPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.ShareCellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.pedia.PediaHomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.EditUserPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.NewUserPage;
import fr.peralta.mycellar.interfaces.client.web.resources.css.CssReferences;
import fr.peralta.mycellar.interfaces.client.web.resources.img.ImageReferences;
import fr.peralta.mycellar.interfaces.client.web.resources.js.JavaScriptReferences;
import fr.peralta.mycellar.interfaces.client.web.shared.ExceptionListener;

/**
 * @author speralta
 */
public abstract class MyCellarWebApplication extends SwarmWebApplication {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        super.init();
        getMarkupSettings().setStripWicketTags(true);
        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this, getApplicationContext()));
        getRequestCycleListeners().add(new ExceptionListener());
        mountResource("/img/glyphicons-halflings.png", ImageReferences.getGlyphiconsImage());
        mountResource("/img/glyphicons-halflings-white.png",
                ImageReferences.getGlyphiconsWhiteImage());
        mountResource("/css/bootstrap.min.css", CssReferences.getBootstrapCss());
        mountResource("/css/bootstrap-responsive.min.css",
                CssReferences.getBootstrapResponsiveCss());
        mountResource("/css/bootstrap-datepicker.css", CssReferences.getBootstrapDatePickerCss());
        mountResource("/css/master.css", CssReferences.getMasterCss());
        mountResource("/css/master-responsive.css", CssReferences.getMasterResponsiveCss());
        mountResource("/js/jquery-1.7.1.min.js", JavaScriptReferences.getJqueryJs());
        mountResource("/js/bootstrap.min.js", JavaScriptReferences.getBootstrapJs());
        mountResource("/js/bootstrap-datepicker.js",
                JavaScriptReferences.getBootstrapDatePickerJs());
        mountResource("/js/master.js", JavaScriptReferences.getMasterJs());
        mountPage("/home", getHomePage());
        mountPage("/cellars", CellarsPage.class);
        mountPage("/io", InputOutputPage.class);
        mountPage("/packageArrival", PackageArrivalPage.class);
        mountPage("/drinkBottles", DrinkBottlesPage.class);
        mountPage("/pedia", PediaHomePage.class);
        mountPage("/newUser", NewUserPage.class);
        mountPage("/editUser", EditUserPage.class);
        mountPage("/listUsers", ListUsersPage.class);
        mountPage("/admin", AdminPage.class);
        mountPage("/error", InternalErrorPage.class);
        mountPage("/stack", StackPage.class);
        mountPage("/shares", ShareCellarsPage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Page> getLoginPage() {
        return HomePage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getHiveKey() {
        return getServletContext().getContextPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IConverterLocator newConverterLocator() {
        ConverterLocator converterLocator = new ConverterLocator();
        converterLocator.set(LocalDate.class, new LocalDateConverter());
        converterLocator.set(WineTypeEnum.class, new WineTypeEnumConverter());
        converterLocator.set(WineColorEnum.class, new WineColorEnumConverter());
        converterLocator.set(AccessRightEnum.class, new AccessRightEnumConverter());
        return converterLocator;
    }

    protected abstract ApplicationContext getApplicationContext();

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

}
