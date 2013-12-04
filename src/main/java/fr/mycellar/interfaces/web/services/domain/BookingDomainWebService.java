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
package fr.mycellar.interfaces.web.services.domain;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.booking.BookingServiceFacade;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/domain/booking")
public class BookingDomainWebService {

    private BookingServiceFacade bookingServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // BOOKING
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Booking> getBookings(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Booking.class);
        List<Booking> bookings;
        if (count == 0) {
            bookings = new ArrayList<>();
        } else {
            bookings = bookingServiceFacade.getBookings(searchParameters);
        }
        return new ListWithCount<>(bookingServiceFacade.countBookings(searchParameters), bookings);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("booking/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Booking getBookingById(@PathParam("id") int bookingId) {
        return bookingServiceFacade.getBookingById(bookingId);
    }

    @DELETE
    @Path("booking/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_BOOKING') and @currentUserService.isCurrentUser(@bookingServiceFacade.getBookingById(#bookingId).customer))")
    public void deleteBookingById(@PathParam("id") int bookingId) throws BusinessException {
        bookingServiceFacade.deleteBooking(bookingServiceFacade.getBookingById(bookingId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("booking")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_BOOKING') and @currentUserService.isCurrentUser(#booking.customer))")
    public Booking saveBooking(Booking booking) throws BusinessException {
        return bookingServiceFacade.saveBooking(booking);
    }

    // --------------
    // BOOKING EVENT
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookingEvents")
    public ListWithCount<BookingEvent> getBookingEvents(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, BookingEvent.class);
        List<BookingEvent> bookingEvents;
        if (count == 0) {
            bookingEvents = new ArrayList<>();
        } else {
            bookingEvents = bookingServiceFacade.getBookingEvents(searchParameters);
        }
        return new ListWithCount<>(bookingServiceFacade.countBookingEvents(searchParameters), bookingEvents);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookingEvent/{id}")
    public BookingEvent getBookingEventById(@PathParam("id") int bookingEventId) {
        return bookingServiceFacade.getBookingEventById(bookingEventId);
    }

    @DELETE
    @Path("bookingEvent/{id}")
    public void deleteBookingEventById(@PathParam("id") int bookingEventId) throws BusinessException {
        bookingServiceFacade.deleteBookingEvent(bookingServiceFacade.getBookingEventById(bookingEventId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bookingEvent")
    public BookingEvent saveBookingEvent(BookingEvent bookingEvent) throws BusinessException {
        return bookingServiceFacade.saveBookingEvent(bookingEvent);
    }

    // BEANS Methods

    @Inject
    public void setBookingServiceFacade(BookingServiceFacade bookingServiceFacade) {
        this.bookingServiceFacade = bookingServiceFacade;
    }

    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}
