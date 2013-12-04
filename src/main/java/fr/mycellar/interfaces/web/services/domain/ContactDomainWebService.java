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

import fr.mycellar.domain.contact.Contact;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;
import fr.mycellar.interfaces.facades.contact.ContactServiceFacade;
import fr.mycellar.interfaces.web.services.FilterCouple;
import fr.mycellar.interfaces.web.services.ListWithCount;
import fr.mycellar.interfaces.web.services.OrderCouple;
import fr.mycellar.interfaces.web.services.SearchParametersUtil;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/domain/contact")
public class ContactDomainWebService {

    private ContactServiceFacade contactServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // BOOKING
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contacts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListWithCount<Contact> getContacts(@QueryParam("first") int first, @QueryParam("count") int count, @QueryParam("filters") List<FilterCouple> filters,
            @QueryParam("sort") List<OrderCouple> orders) {
        SearchParameters searchParameters = searchParametersUtil.getSearchParametersForListWithCount(first, count, filters, orders, Contact.class);
        List<Contact> contacts;
        if (count == 0) {
            contacts = new ArrayList<>();
        } else {
            contacts = contactServiceFacade.getContacts(searchParameters);
        }
        return new ListWithCount<>(contactServiceFacade.countContacts(searchParameters), contacts);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contact/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Contact getContactById(@PathParam("id") int contactId) {
        return contactServiceFacade.getContactById(contactId);
    }

    @DELETE
    @Path("contact/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteContactById(@PathParam("id") int contactId) throws BusinessException {
        contactServiceFacade.deleteContact(contactServiceFacade.getContactById(contactId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contact")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Contact saveContact(Contact contact) throws BusinessException {
        return contactServiceFacade.saveContact(contact);
    }

    // BEANS Methods

    @Inject
    public void setContactServiceFacade(ContactServiceFacade contactServiceFacade) {
        this.contactServiceFacade = contactServiceFacade;
    }

    @Inject
    public void setSearchParametersUtil(SearchParametersUtil searchParametersUtil) {
        this.searchParametersUtil = searchParametersUtil;
    }

}
