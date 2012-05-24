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
package fr.peralta.mycellar.interfaces.client.web.components.booking.autocomplete;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;

import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.SimpleAutoComplete;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingEventSimpleAutoComplete extends SimpleAutoComplete<BookingEvent> {

    private static final long serialVersionUID = 201205221854L;

    @SpringBean
    private BookingServiceFacade bookingServiceFacade;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public BookingEventSimpleAutoComplete(String id, IModel<String> label) {
        super(id, label, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectAutoCompleteBuilder<BookingEvent, Integer> createAutocomplete() {
        return new ObjectAutoCompleteBuilder<BookingEvent, Integer>(
                new BookingEventAutoCompleteProvider());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookingEvent getObject(Integer id) {
        return bookingServiceFacade.getBookingEventById(id);
    }
}
