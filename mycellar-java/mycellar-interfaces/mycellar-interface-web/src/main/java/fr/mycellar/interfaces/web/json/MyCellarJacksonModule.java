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
package fr.mycellar.interfaces.web.json;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.module.SimpleModule;

import fr.peralta.mycellar.domain.booking.BookingBottle;

/**
 * @author speralta
 */
@Named
@Singleton
public class MyCellarJacksonModule extends SimpleModule {
    private static final long serialVersionUID = 201307180835L;

    @Inject
    public MyCellarJacksonModule(BookingBottleKeyDeserializer bookingBottleKeyDeserializer, BookingBottleKeySerializer bookingBottleKeySerializer) {
        super(new MyCellarJacksonVersion());
        // first deserializers
        addKeyDeserializer(BookingBottle.class, bookingBottleKeyDeserializer);

        // then serializers
        addKeySerializer(BookingBottle.class, bookingBottleKeySerializer);
    }

}
