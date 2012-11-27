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
package fr.peralta.mycellar.interfaces.client.web.pages.contact;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.contact.data.LastContactsDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.ActionsColumn;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerSimpleTypeahead;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.BookingSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public class ContactsPage extends BookingSuperPage {

    private static final long serialVersionUID = 201206290734L;
    private static final Logger logger = LoggerFactory.getLogger(ContactsPage.class);

    /**
     * @return
     */
    private static List<IColumn<Contact, ContactOrderEnum>> getColumns() {
        List<IColumn<Contact, ContactOrderEnum>> columns = new ArrayList<IColumn<Contact, ContactOrderEnum>>();
        columns.add(new PropertyColumn<Contact, ContactOrderEnum>(new StringResourceModel(
                "producer.name", null), "producer.name"));
        columns.add(new PropertyColumn<Contact, ContactOrderEnum>(new StringResourceModel(
                "currentDate", null), "current"));
        columns.add(new PropertyColumn<Contact, ContactOrderEnum>(new StringResourceModel(
                "nextDate", null), "next"));
        columns.add(new ActionsColumn<Contact, ContactOrderEnum>(true, false, true));
        return columns;
    }

    private ProducerSimpleTypeahead producerSimpleTypeahead;

    /**
     * @param parameters
     */
    public ContactsPage(PageParameters parameters) {
        super(parameters);
        setOutputMarkupId(true);
        add((producerSimpleTypeahead = new ProducerSimpleTypeahead("producer",
                new StringResourceModel("producer", null), new SearchFormModel(new SearchForm())))
                .setDefaultModel(new Model<Producer>()));
        add(new AdvancedTable<Contact, ContactOrderEnum>("contacts", getColumns(),
                new LastContactsDataProvider(), 30));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                if (event.getSource() instanceof ActionLink) {
                    Contact object = ((Contact) ((ActionLink) event.getSource()).getParent()
                            .getDefaultModelObject());
                    setResponsePage(ContactPage.class,
                            ContactPage.getPageParameters(object.getProducer()));
                    event.stop();
                }
                break;
            case ADD:
                if (event.getSource() instanceof ActionLink) {
                    Producer producer = null;
                    setResponsePage(ContactPage.class, ContactPage.getPageParameters(producer));
                    event.stop();
                }
                break;
            case MODEL_CHANGED:
                if (event.getSource() == producerSimpleTypeahead) {
                    setResponsePage(ContactPage.class,
                            ContactPage.getPageParameters(producerSimpleTypeahead
                                    .getModelObject()));
                    event.stop();
                }
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}
