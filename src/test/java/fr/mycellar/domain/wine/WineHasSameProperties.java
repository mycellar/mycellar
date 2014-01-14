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
package fr.mycellar.domain.wine;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import fr.mycellar.domain.shared.NamedEntityHasSameProperties;

/**
 * @author speralta
 */
public class WineHasSameProperties extends NamedEntityHasSameProperties<Wine> {

    public WineHasSameProperties(Wine object) {
        super(object);
        addNullableProperty("appellation", object.getAppellation(), AppellationHasSameProperties.class);
        addProperty("color", is(equalTo(object.getColor())));
        addProperty("description", is(equalTo(object.getDescription())));
        addProperty("photoUrl", is(equalTo(object.getPhotoUrl())));
        addNullableProperty("producer", object.getProducer(), ProducerHasSameProperties.class);
        addProperty("ranking", is(equalTo(object.getRanking())));
        addProperty("type", is(equalTo(object.getType())));
        addProperty("vintage", is(equalTo(object.getVintage())));
    }

}
