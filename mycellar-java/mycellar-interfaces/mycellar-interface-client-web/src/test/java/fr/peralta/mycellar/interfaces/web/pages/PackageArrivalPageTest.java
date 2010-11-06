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

import static org.easymock.EasyMock.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import fr.peralta.mycellar.interfaces.client.web.pages.PackageArrivalPage;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.dto.Country;

/**
 * @author speralta
 */
public class PackageArrivalPageTest extends AbstractPageTest {

    @Test
    public void constructor() {
        WineServiceFacade wineServiceFacade = createMock(WineServiceFacade.class);
        StockServiceFacade stockServiceFacade = createMock(StockServiceFacade.class);

        Map<Country, Integer> map = new HashMap<Country, Integer>();
        Country country = new Country();
        country.setName("Toto");
        map.put(country, 10);
        expect(wineServiceFacade.getCountriesWithCounts()).andReturn(map)
                .times(2);

        replay(wineServiceFacade, stockServiceFacade);
        getApplicationContext().putBean("wineServiceFacade", wineServiceFacade);
        getApplicationContext().putBean("stockServiceFacade",
                stockServiceFacade);

        getWicketTester().startPage(PackageArrivalPage.class);
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
        getWicketTester().clickLink("form:bottles:addBottle");
        FormTester formTester = getWicketTester().newFormTester("form");
        formTester.setValue("otherCharges", "0");
        getWicketTester().clickLink(
                "form:bottles:bottle:newBottle:country:cloud:0:object");
        FormTester bottleFormTester = getWicketTester().newFormTester(
                "form:bottles:bottle");
        bottleFormTester.setValue("newBottle:quantity", "2");
        bottleFormTester.submit();
        getWicketTester().assertRenderedPage(PackageArrivalPage.class);
        verify(wineServiceFacade, stockServiceFacade);
    }
}
