/*
 * Copyright 2011, MyBooking
 *
 * This file is part of MyBooking.
 *
 * MyBooking is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyBooking is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyBooking. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.interfaces.client.web.pages.admin.contact;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.contact.form.ContactForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.contact.ContactServiceFacade;

/**
 * @author speralta
 */
public class ContactPage extends AbstractEditPage<Contact> {

    private static final long serialVersionUID = 201203270918L;

    private static final String CONTACT_ID_PARAMETER = "contact";

    /**
     * @param contact
     * @return
     */
    public static PageParameters getPageParameters(Contact contact) {
        return new PageParameters().add(CONTACT_ID_PARAMETER, contact.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(CONTACT_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private ContactServiceFacade contactServiceFacade;

    /**
     * @param parameters
     */
    public ContactPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Contact getObjectById(Integer objectId) {
        return contactServiceFacade.getContactById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Contact createNewObject() {
        return new Contact();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return CONTACT_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Contact object) throws BusinessException {
        contactServiceFacade.saveContact(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Contact> createObjectForm(String id, IModel<SearchForm> searchFormModel,
            Contact object) {
        return new ContactForm(id, searchFormModel, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Contact, ?, ?>> getListPageClass() {
        return ContactsPage.class;
    }

}
