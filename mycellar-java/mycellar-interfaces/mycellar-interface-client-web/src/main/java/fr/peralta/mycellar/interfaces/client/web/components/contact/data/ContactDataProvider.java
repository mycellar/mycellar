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
package fr.peralta.mycellar.interfaces.client.web.components.contact.data;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.contact.ContactServiceFacade;

/**
 * @author speralta
 * 
 */
public class ContactDataProvider extends
        MultipleSortableDataProvider<Contact, ContactOrderEnum, ContactOrder> {

    private static final long serialVersionUID = 201109192010L;

    @SpringBean
    private ContactServiceFacade contactServiceFacade;

    /**
     * 
     */
    public ContactDataProvider() {
        super(new ContactOrder().add(ContactOrderEnum.PRODUCER_NAME, OrderWayEnum.ASC).add(
                ContactOrderEnum.CURRENT_DATE, OrderWayEnum.ASC));
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends Contact> iterator(long first, long count) {
        return contactServiceFacade.getContacts(getState().getOrders(), first, count).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return contactServiceFacade.countContacts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Contact> model(Contact object) {
        return new Model<Contact>(object);
    }
}
