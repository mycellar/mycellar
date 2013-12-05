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
package fr.mycellar.domain.shared.exception;

import javax.persistence.metamodel.Attribute;

import fr.mycellar.domain.admin.Configuration_;
import fr.mycellar.domain.booking.BookingEvent_;
import fr.mycellar.domain.contact.Contact_;
import fr.mycellar.domain.stock.Bottle_;
import fr.mycellar.domain.stock.CellarShare_;
import fr.mycellar.domain.stock.Cellar_;
import fr.mycellar.domain.user.ResetPasswordRequest_;
import fr.mycellar.domain.user.User_;
import fr.mycellar.domain.wine.Appellation_;
import fr.mycellar.domain.wine.Country_;
import fr.mycellar.domain.wine.Format_;
import fr.mycellar.domain.wine.Producer_;
import fr.mycellar.domain.wine.Region_;
import fr.mycellar.domain.wine.Wine_;

/**
 * @author speralta
 */
public enum BusinessError {
    /*
     * Appellation
     */
    APPELLATION_00001(Appellation_.region), //
    APPELLATION_00002(Appellation_.name, Appellation_.region), //
    APPELLATION_00003(Appellation_.wines), //

    /*
     * Booking event
     */
    BOOKINGEVENT_00001(BookingEvent_.bookings), //

    /*
     * Bottle
     */
    BOTTLE_00001(Bottle_.wine, Bottle_.format), //

    /*
     * Cellar
     */
    CELLAR_00001(Cellar_.name), //
    CELLAR_00002(Cellar_.stocks), //
    CELLAR_00003(Cellar_.shares), //

    /*
     * CellarShare
     */
    CELLAR_SHARE_00001(CellarShare_.email, CellarShare_.cellar), //

    /*
     * Configuration
     */
    CONFIGURATION_00001(Configuration_.key), //

    /*
     * Contact
     */
    CONTACT_00001(Contact_.producer), //
    CONTACT_00002(Contact_.producer), //
    CONTACT_00003(Contact_.current), //

    /*
     * Country
     */
    COUNTRY_00001(Country_.name), //
    COUNTRY_00002(Country_.regions), //

    /*
     * Format
     */
    FORMAT_00001(Format_.name), //
    FORMAT_00002(Format_.bookingBottles), //
    FORMAT_00003(Format_.stocks), //

    /*
     * Producer
     */
    PRODUCER_00001(Producer_.name), //
    PRODUCER_00002(Producer_.wines), //

    /*
     * Region
     */
    REGION_00001(Region_.country), //
    REGION_00002(Region_.country, Region_.name), //
    REGION_00003(Region_.appellations), //

    /*
     * ResetPasswordRequest
     */
    RESETPASSWORDREQUEST_00001(ResetPasswordRequest_.key), //

    /*
     * User
     */
    USER_00001(User_.email), //
    USER_00002(User_.bookings), //
    USER_00003(User_.cellars), //

    /*
     * Wine
     */
    WINE_00001(Wine_.name), //
    WINE_00002(Wine_.stocks), //
    WINE_00003(Wine_.bookingBottles), //

    /*
     * Other
     */
    OTHER_00001, //
    OTHER_00002, //
    ;

    private final Attribute<?, ?>[] properties;

    /**
     * @param properties
     */
    private BusinessError(Attribute<?, ?>... properties) {
        this.properties = properties;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return getClass().getSimpleName() + "." + name();
    }

    /**
     * @return the properties
     */
    public Attribute<?, ?>[] getProperties() {
        return properties;
    }

}
