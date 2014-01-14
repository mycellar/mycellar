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
package fr.mycellar.domain;

import org.hamcrest.Factory;

import fr.mycellar.domain.image.Image;
import fr.mycellar.domain.image.ImageHasSameProperties;
import fr.mycellar.domain.position.Map;
import fr.mycellar.domain.position.MapHasSameProperties;
import fr.mycellar.domain.position.Position;
import fr.mycellar.domain.position.PositionHasSameProperties;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.BottleHasSameProperties;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarHasSameProperties;
import fr.mycellar.domain.stock.Input;
import fr.mycellar.domain.stock.InputHasSameProperties;
import fr.mycellar.domain.stock.Output;
import fr.mycellar.domain.stock.OutputHasSameProperties;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.stock.StockHasSameProperties;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.user.UserHasSameProperties;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.AppellationHasSameProperties;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.CountryHasSameProperties;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.FormatHasSameProperties;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.ProducerHasSameProperties;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.RegionHasSameProperties;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.WineHasSameProperties;
import fr.mycellar.test.matchers.PropertiesMatcher;

/**
 * @author speralta
 */
public class DomainMatchers {

    @Factory
    public static PropertiesMatcher<? super Appellation> hasSameProperties(Appellation appellation) {
        return new AppellationHasSameProperties(appellation);
    }

    @Factory
    public static PropertiesMatcher<? super Bottle> hasSameProperties(Bottle bottle) {
        return new BottleHasSameProperties(bottle);
    }

    @Factory
    public static PropertiesMatcher<? super Cellar> hasSameProperties(Cellar cellar) {
        return new CellarHasSameProperties(cellar);
    }

    @Factory
    public static PropertiesMatcher<? super Country> hasSameProperties(Country country) {
        return new CountryHasSameProperties(country);
    }

    @Factory
    public static PropertiesMatcher<? super Format> hasSameProperties(Format format) {
        return new FormatHasSameProperties(format);
    }

    @Factory
    public static PropertiesMatcher<? super Image> hasSameProperties(Image image) {
        return new ImageHasSameProperties(image);
    }

    @Factory
    public static PropertiesMatcher<? super Input> hasSameProperties(Input input) {
        return new InputHasSameProperties(input);
    }

    @Factory
    public static PropertiesMatcher<? super Map> hasSameProperties(Map map) {
        return new MapHasSameProperties(map);
    }

    @Factory
    public static PropertiesMatcher<? super Output> hasSameProperties(Output output) {
        return new OutputHasSameProperties(output);
    }

    @Factory
    public static PropertiesMatcher<? super Position> hasSameProperties(Position position) {
        return new PositionHasSameProperties(position);
    }

    @Factory
    public static PropertiesMatcher<? super Producer> hasSameProperties(Producer producer) {
        return new ProducerHasSameProperties(producer);
    }

    @Factory
    public static PropertiesMatcher<? super Region> hasSameProperties(Region region) {
        return new RegionHasSameProperties(region);
    }

    @Factory
    public static PropertiesMatcher<? super Stock> hasSameProperties(Stock stock) {
        return new StockHasSameProperties(stock);
    }

    @Factory
    public static PropertiesMatcher<? super User> hasSameProperties(User user) {
        return new UserHasSameProperties(user);
    }

    @Factory
    public static PropertiesMatcher<? super Wine> hasSameProperties(Wine wine) {
        return new WineHasSameProperties(wine);
    }

    /**
     * Refuse instanciation.
     */
    private DomainMatchers() {

    }

}
