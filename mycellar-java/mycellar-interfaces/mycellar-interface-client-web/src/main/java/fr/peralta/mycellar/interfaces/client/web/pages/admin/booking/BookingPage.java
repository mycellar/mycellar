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
package fr.peralta.mycellar.interfaces.client.web.pages.admin.booking;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.ValidationError;

import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.booking.form.BookingForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
public class BookingPage extends AbstractEditPage<Booking> {

    private static final long serialVersionUID = 201203270918L;

    private static final String BOOKING_ID_PARAMETER = "booking";

    /**
     * @param booking
     * @return
     */
    public static PageParameters getPageParameters(Booking booking) {
        return new PageParameters().add(BOOKING_ID_PARAMETER, booking.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(BOOKING_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private BookingServiceFacade bookingServiceFacade;

    /**
     * @param parameters
     */
    public BookingPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Booking getObjectById(Integer objectId) {
        return bookingServiceFacade.getBookingById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Booking createNewObject() {
        return new Booking();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return BOOKING_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Booking object) {
        try {
            bookingServiceFacade.saveBooking(object);
        } catch (BusinessException e) {
            get(e.getBusinessError().getProperty()).get(e.getBusinessError().getProperty()).error(
                    new ValidationError().addMessageKey(e.getBusinessError().getKey()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Booking> getObjectForm(String id, IModel<SearchForm> searchFormModel,
            Booking object) {
        return new BookingForm(id, searchFormModel, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Booking, ?, ?>> getListPageClass() {
        return BookingsPage.class;
    }

}
