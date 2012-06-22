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
package fr.peralta.mycellar.application.contact.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.contact.ContactService;
import fr.peralta.mycellar.application.shared.AbstractEntityService;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.domain.contact.repository.ContactRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 */
@Service
public class ContactServiceImpl extends
        AbstractEntityService<Contact, ContactOrderEnum, ContactOrder, ContactRepository> implements
        ContactService {

    private ContactRepository contactRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Contact entity) throws BusinessException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContactRepository getRepository() {
        return contactRepository;
    }

    /**
     * @param contactRepository
     *            the contactRepository to set
     */
    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

}
