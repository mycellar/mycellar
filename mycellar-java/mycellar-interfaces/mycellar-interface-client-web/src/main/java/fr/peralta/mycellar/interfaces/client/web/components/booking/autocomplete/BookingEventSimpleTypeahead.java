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

import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.AbstractTypeaheadComponent;
import fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete.SimpleTypeahead;

/**
 * @author speralta
 */
public class BookingEventSimpleTypeahead extends SimpleTypeahead<BookingEvent> {

    private static final long serialVersionUID = 201205221854L;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     */
    public BookingEventSimpleTypeahead(String id, IModel<String> label) {
        super(id, label, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractTypeaheadComponent<BookingEvent> createAutocomplete(String id) {
        return new BookingEventTypeaheadComponent(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FilterEnum getFilterToReplace() {
        return null;
    }

}
