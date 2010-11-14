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
package fr.peralta.mycellar.interfaces.web.pages;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.peralta.mycellar.interfaces.client.web.pages.PackageArrivalPage;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.facades.stock.Arrival;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.Country;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
@RunWith(MockitoJUnitRunner.class)
public class PackageArrivalPageTest extends AbstractPageTest {

    @Mock
    private WineServiceFacade wineServiceFacade;

    @Mock
    private StockServiceFacade stockServiceFacade;

    @Mock
    private RendererServiceFacade rendererServiceFacade;

    @Test
    public void selectCountry() {
        Map<Country, Integer> map = new HashMap<Country, Integer>();
        Country country = new Country();
        country.setName("Toto");
        map.put(country, 10);
        given(wineServiceFacade.getCountriesWithCounts()).willReturn(map);

        getApplicationContext().putBean("wineServiceFacade", wineServiceFacade);
        getApplicationContext().putBean("stockServiceFacade", stockServiceFacade);
        getApplicationContext().putBean("rendererServiceFacade", rendererServiceFacade);

        getWicketTester().startPage(PackageArrivalPage.class);
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
        getWicketTester().clickLink("form:arrivalBottles:addBottle");
        FormTester formTester = getWicketTester().newFormTester("form");
        formTester.setValue("otherCharges", "0");
        getWicketTester()
                .clickLink(
                        "form:arrivalBottles:arrivalBottle:newObject:bottle.wine.appellation.region.country:cloud:0:object");
        FormTester bottleFormTester = getWicketTester().newFormTester(
                "form:arrivalBottles:arrivalBottle");
        bottleFormTester.setValue("newObject:quantity", "2");
        bottleFormTester.submit();
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
    }

    @Test
    public void createCountry() {
        Map<Country, Integer> map = new HashMap<Country, Integer>();
        Country country = new Country();
        country.setName("Toto");
        map.put(country, 10);
        given(wineServiceFacade.getCountriesWithCounts()).willReturn(map);

        getApplicationContext().putBean("wineServiceFacade", wineServiceFacade);
        getApplicationContext().putBean("stockServiceFacade", stockServiceFacade);
        getApplicationContext().putBean("rendererServiceFacade", rendererServiceFacade);

        getWicketTester().startPage(PackageArrivalPage.class);
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
        getWicketTester().clickLink("form:arrivalBottles:addBottle");

        FormTester formTester = getWicketTester().newFormTester("form");
        formTester.setValue("otherCharges", "0");

        getWicketTester()
                .clickLink(
                        "form:arrivalBottles:arrivalBottle:newObject:bottle.wine.appellation.region.country:add");

        FormTester countryFormTester = getWicketTester()
                .newFormTester(
                        "form:arrivalBottles:arrivalBottle:newObject:bottle.wine.appellation.region.country:createForm");
        countryFormTester.setValue("newObject:name", "Nom");
        countryFormTester.submit();

        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
        getWicketTester()
                .assertLabel(
                        "form:arrivalBottles:arrivalBottle:newObject:bottle.wine.appellation.region.country:value",
                        "Nom");

        FormTester bottleFormTester = getWicketTester().newFormTester(
                "form:arrivalBottles:arrivalBottle");
        bottleFormTester.setValue("newObject:quantity", "2");
        bottleFormTester.submit();
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
    }

    @Test
    public void arrival() {
        Map<Country, Integer> map = new HashMap<Country, Integer>();
        Country country = new Country();
        country.setName("Toto");
        map.put(country, 10);
        given(wineServiceFacade.getCountriesWithCounts()).willReturn(map);

        getApplicationContext().putBean("wineServiceFacade", wineServiceFacade);
        getApplicationContext().putBean("stockServiceFacade", stockServiceFacade);
        getApplicationContext().putBean("rendererServiceFacade", rendererServiceFacade);

        getWicketTester().startPage(PackageArrivalPage.class);
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
        getWicketTester().clickLink("form:arrivalBottles:addBottle");
        FormTester formTester = getWicketTester().newFormTester("form");
        formTester.setValue("otherCharges", "0");
        getWicketTester()
                .clickLink(
                        "form:arrivalBottles:arrivalBottle:newObject:bottle.wine.appellation.region.country:cloud:0:object");
        FormTester bottleFormTester = getWicketTester().newFormTester(
                "form:arrivalBottles:arrivalBottle");
        bottleFormTester.setValue("newObject:quantity", "2");
        bottleFormTester.submit();
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);

        getWicketTester().newFormTester("form").submit();

        doNothing().when(stockServiceFacade).arrival((Arrival) anyObject());
    }
}
