/*
 * Copyright 2011, MyBooking
 *
 * This file is part of MyBooking.
 *
 * MyBooking is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyBooking is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyBooking. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.components.booking.form;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.booking.edit.BookingEditPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;

/**
 * @author speralta
 */
public class BookingForm extends ObjectForm<Booking> {

    private static final long serialVersionUID = 201205101327L;

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     */
    public BookingForm(String id, IModel<SearchForm> searchFormModel, Booking newObject) {
        super(id, searchFormModel, newObject);
    }

    /**
     * @param id
     * @param searchFormModel
     */
    public BookingForm(String id, IModel<SearchForm> searchFormModel) {
        super(id, searchFormModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createEditPanel(String id, IModel<SearchForm> searchFormModel) {
        return new BookingEditPanel(id);
    }

}
