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
package fr.mycellar.interfaces.facades.contact;

import java.util.List;

import jpasearch.repository.query.SearchParameters;
import fr.mycellar.domain.contact.Contact;
import fr.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
public interface ContactServiceFacade {

    long countContacts(SearchParameters<Contact> search);

    long countContactsLike(String input, SearchParameters<Contact> search);

    long countLastContacts(String input, SearchParameters<Contact> search);

    Contact getContactById(Integer objectId);

    List<Contact> getContacts(SearchParameters<Contact> search);

    List<Contact> getContactsLike(String input, SearchParameters<Contact> search);

    List<Contact> getLastContacts(String input, SearchParameters<Contact> search);

    Contact saveContact(Contact contact) throws BusinessException;

    void deleteContact(Contact contact) throws BusinessException;

}
