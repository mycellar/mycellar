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
package fr.peralta.mycellar.domain.contact.repository;

import java.util.List;

import org.joda.time.LocalDate;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.shared.repository.EntityRepository;
import fr.peralta.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
public interface ContactRepository extends
        EntityRepository<Contact, ContactOrderEnum, ContactOrder> {

    /**
     * @return
     */
    List<Contact> getAllToContact();

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
    List<Contact> getLastContacts(ContactOrder orders, int first, int count);

    /**
     * @param producer
     * @param current
     * @return
     */
    Contact find(Producer producer, LocalDate current);

    /**
     * @param producer
     * @param orders
     * @param first
     * @param count
     * @return
     */
    List<Contact> getAllForProducer(Producer producer, ContactOrder orders, int first, int count);

    /**
     * @param producer
     * @return
     */
    long countForProducer(Producer producer);

}
