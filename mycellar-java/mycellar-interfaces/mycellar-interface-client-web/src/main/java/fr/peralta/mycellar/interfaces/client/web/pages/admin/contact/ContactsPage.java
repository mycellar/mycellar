/*
 * Copyright 2011, MyContact
 *
 * This file is part of MyContact.
 *
 * MyContact is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyContact is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyContact. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.pages.admin.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.contact.data.ContactDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;

/**
 * @author speralta
 */
public class ContactsPage extends AbstractListPage<Contact, ContactOrderEnum, ContactOrder> {

    private static final long serialVersionUID = 201203262250L;

    /**
     * @param parameters
     */
    public ContactsPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MultipleSortableDataProvider<Contact, ContactOrderEnum, ContactOrder> getDataProvider() {
        return new ContactDataProvider();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<IColumn<Contact>> getColumns() {
        List<IColumn<Contact>> columns = new ArrayList<IColumn<Contact>>();
        columns.add(new PropertyColumn<Contact>(new ResourceModel("producer.name"),
                ContactOrderEnum.PRODUCER_NAME.name(), "producer.name"));
        columns.add(new PropertyColumn<Contact>(new ResourceModel("current"),
                ContactOrderEnum.CURRENT_DATE.name(), "current"));
        columns.add(new PropertyColumn<Contact>(new ResourceModel("next"),
                ContactOrderEnum.NEXT_DATE.name(), "next"));
        return columns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageParameters getEditPageParameters(Contact entity) {
        if (entity == null) {
            return ContactPage.getPageParametersForCreation();
        } else {
            return ContactPage.getPageParameters(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractEditPage<Contact>> getEditPageClass() {
        return ContactPage.class;
    }

}
