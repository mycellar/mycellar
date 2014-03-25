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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;

import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.interfaces.facades.booking.BookingServiceFacade;
import fr.mycellar.interfaces.web.security.CurrentUserService;
import fr.mycellar.interfaces.web.services.ListWithCount;

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
    @Path("bookings")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    public ListWithCount<Booking> getBookings( //
            @QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count) {
        return new ListWithCount<>(bookingServiceFacade.countBookings(currentUserService.getCurrentUser()), bookingServiceFacade.getBookings(currentUserService.getCurrentUser(), first, count));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookings/{id}")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    // TODO @PostAuthorize("") to check booking is owned by
    public Booking getBookingById(@PathParam("id") int bookingId) {
        return bookingServiceFacade.getBookingById(bookingId);
    }

    @DELETE
    @Path("bookings/{id}")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    // TODO @PreAuthorize("") to check booking is owned by
    public void deleteBookingById(@PathParam("id") int bookingId) throws BusinessException {
        bookingServiceFacade.deleteBooking(bookingServiceFacade.getBookingById(bookingId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookings/{id}")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    // TODO @PreAuthorize("") to check booking is owned by
    public Booking saveBooking(@PathParam("id") Integer id, Booking booking) throws BusinessException {
        if ((id == booking.getId()) && (bookingServiceFacade.getBookingById(id) != null)) {
            return bookingServiceFacade.saveBooking(booking);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookings")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    // TODO @PreAuthorize("") to check booking is owned by
    public Booking saveBooking(Booking booking) throws BusinessException {
        if (booking.getId() == null) {
            return bookingServiceFacade.saveBooking(booking);
        }
        throw new RuntimeException();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("currentBookingEvents")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    public ListWithCount<BookingEvent> getCurrentBookingEvents() {
        return new ListWithCount<>(bookingServiceFacade.getCurrentBookingEvents());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookingByBookingEvent")
    @PreAuthorize("hasRole('ROLE_BOOKING')")
    public Booking getBooking(@QueryParam("bookingEventId") Integer bookingEventId) {
        return bookingServiceFacade.getBooking(bookingServiceFacade.getBookingEventById(bookingEventId), currentUserService.getCurrentUser());
    }

    @Inject
    public void setBookingServiceFacade(BookingServiceFacade bookingServiceFacade) {
        this.bookingServiceFacade = bookingServiceFacade;
    }

    @Inject
    public void setCurrentUserService(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

}
