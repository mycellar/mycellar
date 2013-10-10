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
package fr.peralta.mycellar.interfaces.facades.contact;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.contact.ContactService;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named("contactServiceFacade")
@Singleton
public class ContactServiceFacadeImpl implements ContactServiceFacade {

    private ContactService contactService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countContacts(SearchParameters searchParameters) {
        return contactService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countLastContacts(SearchParameters searchParameters) {
        return contactService.countLastContacts(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Contact getContactById(Integer objectId) {
        return contactService.getById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> getContacts(SearchParameters searchParameters) {
        return contactService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> getLastContacts(SearchParameters searchParameters) {
        return contactService.getLastContacts(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Contact saveContact(Contact contact) throws BusinessException {
        return contactService.save(contact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteContact(Contact contact) throws BusinessException {
        contactService.delete(contact);
    }

    /**
     * @param contactService
     *            the contactService to set
     */
    @Inject
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

}
