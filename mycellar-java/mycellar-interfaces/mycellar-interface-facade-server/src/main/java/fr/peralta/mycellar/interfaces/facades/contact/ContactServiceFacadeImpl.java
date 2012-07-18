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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.contact.ContactService;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
@Service
public class ContactServiceFacadeImpl implements ContactServiceFacade {

    private ContactService contactService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countContacts() {
        return contactService.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countLastContacts() {
        return contactService.countLastContacts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countContactsForProducer(Producer producer) {
        return contactService.countForProducer(producer);
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
    public List<Contact> getContacts(ContactOrder orders, int first, int count) {
        return contactService.getAll(orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> getLastContacts(ContactOrder orders, int first, int count) {
        return contactService.getLastContacts(orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Contact> getContactsForProducer(Producer producer, ContactOrder orders, int first,
            int count) {
        return contactService.getAllForProducer(producer, orders, first, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void saveContact(Contact contact) throws BusinessException {
        contactService.save(contact);
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
    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

}
