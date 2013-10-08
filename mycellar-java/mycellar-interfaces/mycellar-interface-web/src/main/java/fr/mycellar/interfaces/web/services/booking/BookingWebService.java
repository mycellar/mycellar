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
package fr.mycellar.interfaces.web.services.booking;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.interfaces.web.security.CurrentUserService;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.peralta.mycellar.domain.booking.Booking;
import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.booking.BookingEvent;
import fr.peralta.mycellar.interfaces.facades.booking.BookingServiceFacade;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/booking")
public class BookingWebService {

    private BookingServiceFacade bookingServiceFacade;

    private CurrentUserService currentUserService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("quantities/{bookingEventId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public Map<BookingBottle, Long> getQuantities(@PathParam(value = "bookingEventId") Integer bookingEventId) {
        return bookingServiceFacade.getBookingsQuantities(bookingServiceFacade.getBookingEventById(bookingEventId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("currentBookingEvents")
    @PreAuthorize(value = "hasRole('ROLE_BOOKING')")
    public ListWithCount<BookingEvent> getCurrentBookingEvents() {
        return new ListWithCount<>(bookingServiceFacade.getCurrentBookingEvents());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("booking")
    @PreAuthorize(value = "hasRole('ROLE_BOOKING')")
    public Booking getBooking(@QueryParam(value = "bookingEventId") Integer bookingEventId) {
        return bookingServiceFacade.getBooking(bookingServiceFacade.getBookingEventById(bookingEventId), currentUserService.getCurrentUser());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookings")
    @PreAuthorize(value = "hasRole('ROLE_BOOKING')")
    public ListWithCount<Booking> getBookings() {
        return new ListWithCount<>(bookingServiceFacade.getBookings(currentUserService.getCurrentUser()));
    }

    /**
     * @param bookingServiceFacade
     *            the bookingServiceFacade to set
     */
    @Inject
    public void setBookingServiceFacade(BookingServiceFacade bookingServiceFacade) {
        this.bookingServiceFacade = bookingServiceFacade;
    }

    /**
     * @param currentUserService
     *            the currentUserService to set
     */
    @Inject
    public void setCurrentUserService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

}
