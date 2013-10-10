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

import fr.peralta.mycellar.application.shared.SimpleService;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
public interface ContactService extends SimpleService<Contact> {

    /**
     * @param producer
     * @param current
     * @return
     */
    Contact find(Producer producer, LocalDate current);

    /**
     * 
     */
    void sendReminders();

    /**
     * @param searchParameters
     * @return
     */
    long countLastContacts(SearchParameters searchParameters);

    /**
     * @param searchParameters
     * @return
     */
    List<Contact> getLastContacts(SearchParameters searchParameters);

}
