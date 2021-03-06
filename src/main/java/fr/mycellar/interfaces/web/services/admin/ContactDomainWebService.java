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
package fr.mycellar.interfaces.web.services.admin;

import java.util.ArrayList;
import java.util.List;

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

import jpasearch.repository.query.SearchParameters;
import fr.mycellar.domain.contact.Contact;
import fr.mycellar.domain.shared.exception.BusinessException;
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
@Path("/admin/domain/contact")
public class ContactDomainWebService {

    private ContactServiceFacade contactServiceFacade;

    private SearchParametersUtil searchParametersUtil;

    // --------------
    // CONTACT
    // --------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contacts")
    public ListWithCount<Contact> getContacts(@QueryParam("first") int first, //
            @QueryParam("count") @DefaultValue("10") int count, //
            @QueryParam("filters") List<FilterCouple> filters, //
            @QueryParam("sort") List<OrderCouple> orders, //
            @QueryParam("like") String term) {
        SearchParameters<Contact> searchParameters = searchParametersUtil.getSearchParameters(first, count, filters, orders, Contact.class);
        List<Contact> contacts;
        if (count == 0) {
            contacts = new ArrayList<>();
        } else {
            contacts = contactServiceFacade.getContactsLike(term, searchParameters);
        }
        return new ListWithCount<>(contactServiceFacade.countContactsLike(term, searchParameters), contacts);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contacts/{id}")
    public Contact getContactById(@PathParam("id") int contactId) {
        return contactServiceFacade.getContactById(contactId);
    }

    @DELETE
    @Path("contacts/{id}")
    public void deleteContactById(@PathParam("id") int contactId) throws BusinessException {
        contactServiceFacade.deleteContact(contactServiceFacade.getContactById(contactId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contacts/{id}")
    public Contact saveContact(@PathParam("id") int contactId, Contact contact) throws BusinessException {
        if ((contactId == contact.getId()) && (getContactById(contactId) != null)) {
            return contactServiceFacade.saveContact(contact);
        }
        throw new RuntimeException();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("contacts")
    public Contact saveContact(Contact contact) throws BusinessException {
        if (contact.getId() == null) {
            return contactServiceFacade.saveContact(contact);
        }
        throw new RuntimeException();
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
