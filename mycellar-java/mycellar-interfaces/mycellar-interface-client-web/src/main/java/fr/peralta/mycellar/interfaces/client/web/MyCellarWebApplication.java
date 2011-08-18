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
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.MountedMapper;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;

import fr.peralta.mycellar.interfaces.client.web.converters.LocalDateConverter;
import fr.peralta.mycellar.interfaces.client.web.pages.HomePage;
import fr.peralta.mycellar.interfaces.client.web.pages.ListUsersPage;
import fr.peralta.mycellar.interfaces.client.web.pages.NewUserPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.CellarsPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.InputOutputPage;
import fr.peralta.mycellar.interfaces.client.web.pages.cellar.PackageArrivalPage;
import fr.peralta.mycellar.interfaces.client.web.pages.pedia.PediaHomePage;

/**
 * @author speralta
 */
public abstract class MyCellarWebApplication extends WebApplication {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        super.init();
        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this, getApplicationContext()));
        getRootRequestMapperAsCompound().add(new MountedMapper("/home", getHomePage()));
        getRootRequestMapperAsCompound().add(new MountedMapper("/cellars", CellarsPage.class));
        getRootRequestMapperAsCompound().add(new MountedMapper("/io", InputOutputPage.class));
        getRootRequestMapperAsCompound().add(
                new MountedMapper("/packageArrival", PackageArrivalPage.class));
        getRootRequestMapperAsCompound().add(new MountedMapper("/pedia", PediaHomePage.class));
        getRootRequestMapperAsCompound().add(new MountedMapper("/newUser", NewUserPage.class));
        getRootRequestMapperAsCompound().add(new MountedMapper("/listUsers", ListUsersPage.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IConverterLocator newConverterLocator() {
        ConverterLocator converterLocator = new ConverterLocator();
        converterLocator.set(LocalDate.class, new LocalDateConverter());
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
