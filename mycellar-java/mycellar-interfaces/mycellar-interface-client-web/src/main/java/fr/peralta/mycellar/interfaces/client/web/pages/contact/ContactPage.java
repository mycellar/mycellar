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

import java.util.Iterator;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.repository.ContactOrder;
import fr.peralta.mycellar.domain.contact.repository.ContactOrderEnum;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.contact.form.ContactForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackInlineBorder;
import fr.peralta.mycellar.interfaces.client.web.components.wine.autocomplete.ProducerSimpleTypeahead;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.BookingSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.FormValidationHelper;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;
import fr.peralta.mycellar.interfaces.facades.contact.ContactServiceFacade;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class ContactPage extends BookingSuperPage {

    private final class ContactDataView extends DataView<Contact> {
        private static final long serialVersionUID = 201205231418L;

        /**
         * @param id
         * @param dataProvider
         */
        public ContactDataView(String id, IDataProvider<Contact> dataProvider) {
            super(id, dataProvider, 10);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void populateItem(Item<Contact> item) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.longDate().withLocale(
                    Locale.getDefault());
            item.add(new ActionLink("editContact", Action.SELECT));
            item.add(new Label("current", dateTimeFormatter.print(item.getModelObject()
                    .getCurrent())));
            LocalDate next = item.getModelObject().getNext();
            item.add(new Label("next", next != null ? dateTimeFormatter.print(item.getModelObject()
                    .getNext()) : ""));
            item.add(new MultiLineLabel("text", item.getModelObject().getText()));
        }
    }

    private static class ProducerForm extends Form<Producer> {

        private static final long serialVersionUID = 201207040845L;

        @SpringBean
        private WineServiceFacade wineServiceFacade;

        /**
         * @param id
         * @param model
         */
        public ProducerForm(String id, IModel<Producer> model) {
            super(id, model);
            add(new Label("name"));
            add(new FormComponentFeedbackInlineBorder("phone").add(new TextField<String>("phone")));
            add(new FormComponentFeedbackInlineBorder("fax").add(new TextField<String>("fax")));
            add(new FormComponentFeedbackInlineBorder("email").add(new EmailTextField("email")));
            add(new FormComponentFeedbackInlineBorder("description").add(new TextArea<String>(
                    "description")));
            add(new FormComponentFeedbackInlineBorder("contactInformation")
                    .add(new TextArea<String>("contactInformation")));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit() {
            try {
                wineServiceFacade.saveProducer(getModelObject());
                setResponsePage(ContactPage.class, ContactPage.getPageParameters(getModelObject()));
            } catch (BusinessException e) {
                FormValidationHelper.error(this, e);
            }
        }

    }

    private static final class ContactDataProvider implements IDataProvider<Contact> {

        private static final long serialVersionUID = 201207040935L;

        @SpringBean
        private ContactServiceFacade contactServiceFacade;

        private IModel<Producer> producerModel;

        /**
         * Default constructor.
         */
        public ContactDataProvider() {
            Injector.get().inject(this);
        }

        /**
         * @param producer
         *            the producer to set
         */
        public ContactDataProvider setProducer(Producer producer) {
            producerModel = new Model<Producer>(producer);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void detach() {
            if (producerModel != null) {
                producerModel.detach();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<? extends Contact> iterator(long first, long count) {
            return contactServiceFacade.getContactsForProducer(producerModel.getObject(),
                    new ContactOrder().add(ContactOrderEnum.CURRENT_DATE, OrderWayEnum.DESC),
                    first, count).iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long size() {
            if ((producerModel != null) && (producerModel.getObject() != null)) {
                return contactServiceFacade.countContactsForProducer(producerModel.getObject());
            } else {
                return 0;
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IModel<Contact> model(Contact object) {
            return new Model<Contact>(object);
        }

    }

    private static final long serialVersionUID = 201206290945L;
    private static final Logger logger = LoggerFactory.getLogger(ContactPage.class);

    private static final String PRODUCER_ID_PARAMETER = "producer";

    /**
     * @param producer
     * @return
     */
    public static PageParameters getPageParameters(Producer producer) {
        PageParameters parameters = new PageParameters();
        if ((producer != null) && (producer.getId() != null)) {
            parameters.add(PRODUCER_ID_PARAMETER, producer.getId());
        }
        return parameters;
    }

    @SpringBean
    private WineServiceFacade wineServiceFacade;
    @SpringBean
    private ContactServiceFacade contactServiceFacade;

    private ProducerSimpleTypeahead producerSimpleTypeahead;
    private ProducerForm producerForm;
    private ActionLink addContactLink;
    private ContactDataView contactDataView;
    private ContactDataProvider contactDataProvider;
    private ContactForm contactForm;

    /**
     * @param parameters
     */
    public ContactPage(PageParameters parameters) {
        super(parameters);
        Injector.get().inject(this);
        setOutputMarkupId(true);
        Producer producer = getProducerFormParameters(parameters);
        add((producerForm = new ProducerForm("producer", new CompoundPropertyModel<Producer>(
                producer))).setVisibilityAllowed(producer != null));
        producerForm.add((addContactLink = new ActionLink("addContact", Action.ADD)));
        add((producerSimpleTypeahead = new ProducerSimpleTypeahead("newProducer",
                new StringResourceModel("newProducer", null), new SearchFormModel(new SearchForm())))
                .setVisibilityAllowed(producer == null).setDefaultModel(new Model<Producer>()));
        add((contactDataView = new ContactDataView("contact",
                (contactDataProvider = new ContactDataProvider()).setProducer(producer)))
                .setVisibilityAllowed(producer != null));
        add(createHiddenContactForm());
    }

    /**
     * @param parameters
     * @return
     */
    private Producer getProducerFormParameters(PageParameters parameters) {
        StringValue producerIdValue = parameters.get(PRODUCER_ID_PARAMETER);
        if (producerIdValue.isEmpty()) {
            return null;
        }
        try {
            int producerId = producerIdValue.toInteger();
            return wineServiceFacade.getProducerById(producerId);
        } catch (StringValueConversionException e) {
            throw new RuntimeException(e);
        }
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
            case MODEL_CHANGED:
                if (event.getSource() == producerSimpleTypeahead) {
                    Producer producer = producerSimpleTypeahead.getModelObject();
                    producerForm.setDefaultModel(new CompoundPropertyModel<Producer>(producer));
                    producerForm.setVisibilityAllowed(producer != null);
                    producerSimpleTypeahead.setVisibilityAllowed(producer == null);
                    addContactLink.setVisibilityAllowed(producer != null);
                    contactDataView.setVisibilityAllowed(producer != null);
                    contactDataProvider.setProducer(producer);
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case ADD:
                if (event.getSource() == addContactLink) {
                    Contact contact = new Contact();
                    contact.setProducer(producerForm.getModelObject());
                    contactForm.setNewObject(contact);
                    contactForm.displayForm();
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case SELECT:
                if (event.getSource() instanceof ActionLink) {
                    contactForm.setNewObject((Contact) ((ActionLink) event.getSource()).getParent()
                            .getDefaultModelObject());
                    contactForm.displayForm();
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case SAVE:
                if (contactForm == event.getSource()) {
                    try {
                        contactServiceFacade.saveContact(contactForm.getModelObject());
                        replace(createHiddenContactForm());
                        AjaxTool.ajaxReRender(this);
                        event.stop();
                    } catch (BusinessException e) {
                        FormValidationHelper.error(contactForm, e);
                    }
                }
                break;
            case CANCEL:
                if (contactForm.isCancelButton(event.getSource())) {
                    replace(createHiddenContactForm());
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

    private Component createHiddenContactForm() {
        return (contactForm = new ContactForm("contactForm", new SearchFormModel(new SearchForm()),
                new Contact())).hideForm();
    }
}
