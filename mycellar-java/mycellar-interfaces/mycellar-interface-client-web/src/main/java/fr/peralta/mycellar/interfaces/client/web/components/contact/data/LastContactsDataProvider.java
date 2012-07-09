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
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.contact.ContactServiceFacade;

/**
 * @author speralta
 * 
 */
public class LastContactsDataProvider extends
        MultipleSortableDataProvider<Contact, ContactOrderEnum, ContactOrder> {

    private static final long serialVersionUID = 201109192010L;

    @SpringBean
    private ContactServiceFacade contactServiceFacade;

    private final IModel<Producer> producerModel;

    /**
     * @param producerModel
     */
    public LastContactsDataProvider(IModel<Producer> producerModel) {
        super(new ContactOrder().add(ContactOrderEnum.PRODUCER_NAME, OrderWayEnum.ASC).add(
                ContactOrderEnum.CURRENT_DATE, OrderWayEnum.ASC));
        Injector.get().inject(this);
        this.producerModel = producerModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends Contact> iterator(int first, int count) {
        if ((producerModel.getObject() != null) && (producerModel.getObject().getId() != null)) {
            return contactServiceFacade.getContactsForProducer(producerModel.getObject(),
                    getState().getOrders(), first, count).iterator();
        } else {
            return contactServiceFacade.getLastContacts(getState().getOrders(), first, count)
                    .iterator();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        if ((producerModel.getObject() != null) && (producerModel.getObject().getId() != null)) {
            return 1;
        }
        return (int) contactServiceFacade.countLastContacts();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Contact> model(Contact object) {
        return new Model<Contact>(object);
    }
}
