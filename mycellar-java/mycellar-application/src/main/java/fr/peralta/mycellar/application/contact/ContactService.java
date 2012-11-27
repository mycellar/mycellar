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
package fr.peralta.mycellar.application.contact;

import java.util.List;

import org.joda.time.LocalDate;

import fr.peralta.mycellar.application.shared.EntityService;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
public interface ContactService extends EntityService<Contact, ContactOrderEnum, ContactOrder> {

    /**
     * @param producer
     * @param current
     * @return
     */
    Contact find(Producer producer, LocalDate current);

    void sendReminders();

    /**
     * @return
     */
    long countLastContacts();

    /**
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Contact> getLastContacts(ContactOrder orders, long first, long count);

    /**
     * @param producer
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Contact> getAllForProducer(Producer producer, ContactOrder orders, long first, long count);

    /**
     * @param producer
     * @return
     */
    long countForProducer(Producer producer);

}
