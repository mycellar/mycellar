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

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
public interface ContactServiceFacade {

    /**
     * @return
     */
    long countContacts();

    /**
     * @return
     */
    long countLastContacts();

    /**
     * @param object
     * @return
     */
    long countContactsForProducer(Producer producer);

    /**
     * @param objectId
     * @return
     */
    Contact getContactById(Integer objectId);

    /**
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Contact> getContacts(ContactOrder orders, int first, int count);

    /**
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Contact> getLastContacts(ContactOrder orders, int first, int count);

    /**
     * @param producer
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Contact> getContactsForProducer(Producer producer, ContactOrder orders, int first,
            int count);

    /**
     * @param contact
     * @throws BusinessException
     */
    void saveContact(Contact contact) throws BusinessException;

    /**
     * @param object
     * @throws BusinessException
     */
    void deleteContact(Contact contact) throws BusinessException;

}
