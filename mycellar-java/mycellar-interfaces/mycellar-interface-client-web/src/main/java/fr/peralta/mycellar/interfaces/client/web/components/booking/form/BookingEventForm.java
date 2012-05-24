/*
 * Copyright 2011, MyBookingEvent
 *
 * This file is part of MyBookingEvent.
 *
 * MyBookingEvent is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyBookingEvent is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyBookingEvent. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.components.booking.form;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.booking.edit.BookingEventEditPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;

/**
 * @author speralta
 */
public class BookingEventForm extends ObjectForm<BookingEvent> {

    private static final long serialVersionUID = 201205101327L;

    /**
     * @param id
     * @param searchFormModel
     * @param newObject
     */
    public BookingEventForm(String id, IModel<SearchForm> searchFormModel, BookingEvent newObject) {
        super(id, searchFormModel, newObject);
    }

    /**
     * @param id
     * @param searchFormModel
     */
    public BookingEventForm(String id, IModel<SearchForm> searchFormModel) {
        super(id, searchFormModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createEditPanel(String id, IModel<SearchForm> searchFormModel) {
        return new BookingEventEditPanel(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onSubmit() {
        for (BookingBottle bottle : getModelObject().getBottles()) {
            bottle.setBookingEvent(getModelObject());
        }
        super.onSubmit();
    }

}
