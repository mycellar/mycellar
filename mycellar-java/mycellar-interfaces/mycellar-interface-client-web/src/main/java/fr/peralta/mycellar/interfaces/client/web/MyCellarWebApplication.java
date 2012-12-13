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

import java.io.IOException;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.pages.InternalErrorPage;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.wicketstuff.security.hive.HiveMind;
import org.wicketstuff.security.hive.config.PolicyFileHiveFactory;
import org.wicketstuff.security.hive.config.SwarmPolicyFileHiveFactory;
import org.wicketstuff.security.swarm.SwarmWebApplication;

import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.domain.stock.AccessRightEnum;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.converters.AccessRightEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.ConfigurationKeyEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.LocalDateConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.WineColorEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.converters.WineTypeEnumConverter;
import fr.peralta.mycellar.interfaces.client.web.descriptors.menu.HomePageDescriptor;
import fr.peralta.mycellar.interfaces.client.web.pages.HomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AdminPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.ListPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.NewUsersPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.NewVintagesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.StackPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.admin.ConfigurationPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.admin.ConfigurationsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.booking.BookingEventPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.booking.BookingPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.stock.CellarPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.stock.CellarSharePage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.stock.CellarSharesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.user.UserPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.user.UsersPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.AppellationPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.AppellationsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.CountriesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.CountryPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.FormatPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.FormatsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.ProducersPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.RegionPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.RegionsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.WinePage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.wine.WinesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.booking.BookingEventsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.booking.BookingReportsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.booking.BookingsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.CellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.DrinkBottlesPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.InputOutputPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.PackageArrivalPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.ShareCellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.contact.ContactPage;
import fr.peralta.mycellar.interfaces.client.web.pages.contact.ContactsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.pedia.PediaHomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.MyCellarAccessDeniedPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.LoginPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.MyAccountPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.NewUserPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.ResetPasswordPage;
import fr.peralta.mycellar.interfaces.client.web.pages.user.ResetPasswordRequestPage;
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
        // Strip wicket tags
        getMarkupSettings().setStripWicketTags(true);
        // Add spring injector for @SpringBean
        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this, getApplicationContext()));
        // Add exception listener for saving stack trace in database
        getRequestCycleListeners().add(new ExceptionListener());
        // Set custom access denied page
        getApplicationSettings().setAccessDeniedPage(MyCellarAccessDeniedPage.class);
        // Add resource loader for menu
        getResourceSettings().getStringResourceLoaders().add(
                new ClassStringResourceLoader(HomePageDescriptor.class));
        // Add mounts for img
        mountResource("/img/database.png", ImageReferences.getDatabaseImage());
        mountResource("/img/glyphicons-halflings.png", ImageReferences.getGlyphiconsImage());
        mountResource("/img/glyphicons-halflings-white.png",
                ImageReferences.getGlyphiconsWhiteImage());
        // Add mounts for css
        mountResource("/css/bootstrap.min.css", CssReferences.getBootstrapCss());
        mountResource("/css/bootstrap-responsive.min.css",
                CssReferences.getBootstrapResponsiveCss());
        mountResource("/css/bootstrap-datepicker.css", CssReferences.getBootstrapDatePickerCss());
        mountResource("/css/master.css", CssReferences.getMasterCss());
        mountResource("/css/master-responsive.css", CssReferences.getMasterResponsiveCss());
        // Add mounts for js
        mountResource("/js/bootstrap.min.js", JavaScriptReferences.getBootstrapJs());
        mountResource("/js/bootstrap-datepicker.js",
                JavaScriptReferences.getBootstrapDatePickerJs());
        mountResource("/js/bootstrap-typeaheadkey.js",
                JavaScriptReferences.getBootstrapDatePickerJs());
        mountResource("/js/master.js", JavaScriptReferences.getMasterJs());

        // Add mounts for pages
        mountPage("/home", getHomePage());

        // ** utils pages
        mountPage("/accessDenied", MyCellarAccessDeniedPage.class);
        mountPage("/error", InternalErrorPage.class);
        mountPage("/login", LoginPage.class);
        mountPage("/myaccount", MyAccountPage.class);
        mountPage("/register", NewUserPage.class);
        mountPage("/stack", StackPage.class);
        mountPage("/passwordForgotten", ResetPasswordRequestPage.class);
        mountPage("/passwordReset", ResetPasswordPage.class);

        // ** booking pages
        mountPage("/booking", BookingEventsPage.class);
        mountPage("/booking/mybookings", BookingsPage.class);
        mountPage("/booking/reports", BookingReportsPage.class);
        mountPage("/booking/contacts", ContactsPage.class);
        mountPage("/booking/contact", ContactPage.class);

        // ** cellar pages
        mountPage("/cellars", CellarsPage.class);
        mountPage("/drinkBottles", DrinkBottlesPage.class);
        mountPage("/io", InputOutputPage.class);
        mountPage("/packageArrival", PackageArrivalPage.class);
        mountPage("/shares", ShareCellarsPage.class);

        // ** pedia pages
        mountPage("/pedia", PediaHomePage.class);

        // ** admin
        mountPage("/admin", AdminPage.class);
        mountPage("/admin/newVintages", NewVintagesPage.class);
        mountPage("/admin/newUsers", NewUsersPage.class);

        // ** admin lists
        mountPage("/admin/lists", ListPage.class);
        mountPage("/admin/lists/appellations", AppellationsPage.class);
        mountPage("/admin/lists/bookings",
                fr.peralta.mycellar.interfaces.client.web.pages.admin.booking.BookingsPage.class);
        mountPage(
                "/admin/lists/bookingEvents",
                fr.peralta.mycellar.interfaces.client.web.pages.admin.booking.BookingEventsPage.class);
        mountPage("/admin/lists/cellars",
                fr.peralta.mycellar.interfaces.client.web.pages.admin.stock.CellarsPage.class);
        mountPage("/admin/lists/cellarShares", CellarSharesPage.class);
        mountPage("/admin/lists/configurations", ConfigurationsPage.class);
        mountPage("/admin/lists/contacts",
                fr.peralta.mycellar.interfaces.client.web.pages.admin.contact.ContactsPage.class);
        mountPage("/admin/lists/countries", CountriesPage.class);
        mountPage("/admin/lists/formats", FormatsPage.class);
        mountPage("/admin/lists/producers", ProducersPage.class);
        mountPage("/admin/lists/regions", RegionsPage.class);
        mountPage("/admin/lists/users", UsersPage.class);
        mountPage("/admin/lists/wines", WinesPage.class);

        // ** admin edit
        mountPage("/admin/edit/appellation", AppellationPage.class);
        mountPage("/admin/edit/booking", BookingPage.class);
        mountPage("/admin/edit/bookingEvent", BookingEventPage.class);
        mountPage("/admin/edit/cellar", CellarPage.class);
        mountPage("/admin/edit/cellarShare", CellarSharePage.class);
        mountPage("/admin/edit/configuration", ConfigurationPage.class);
        mountPage("/admin/edit/contact",
                fr.peralta.mycellar.interfaces.client.web.pages.admin.contact.ContactPage.class);
        mountPage("/admin/edit/country", CountryPage.class);
        mountPage("/admin/edit/format", FormatPage.class);
        mountPage("/admin/edit/region", RegionPage.class);
        mountPage("/admin/edit/user", UserPage.class);
        mountPage("/admin/edit/wine", WinePage.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Page> getLoginPage() {
        return LoginPage.class;
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
        converterLocator.set(ConfigurationKeyEnum.class, new ConfigurationKeyEnumConverter());
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUpHive() {
        PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
        try {
            factory.addPolicyFile(new ClassPathResource("mycellar.hive").getURL());
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
        HiveMind.registerHive(getHiveKey(), factory);
    }

}
