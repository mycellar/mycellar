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
package fr.peralta.mycellar.interfaces.client.web.components.booking.edit;

import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.repeater.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.behaviors.NotEmptyCollectionValidator;
import fr.peralta.mycellar.interfaces.client.web.components.booking.form.BookingBottleForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public class BookingBottlesEditPanel extends FormComponentPanel<Set<BookingBottle>> {

    private static final Logger logger = LoggerFactory.getLogger(BookingBottlesEditPanel.class);
    private static final long serialVersionUID = 201202231626L;

    private static final String NO_BOTTLES_COMPONENT_ID = "noBottles";

    private final ActionLink addBottle;
    private BookingBottleForm bookingBottleForm;

    /**
     * @param id
     */
    public BookingBottlesEditPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        add(new BookingBottlesView("bottles"));
        add(addBottle = new ActionLink("addBottle", Action.ADD));
        add(new WebMarkupContainer(NO_BOTTLES_COMPONENT_ID) {
            private static final long serialVersionUID = 201108082329L;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean isVisible() {
                return BookingBottlesEditPanel.this.getModelObject().size() == 0;
            }
        });
        add(new NotEmptyCollectionValidator());
        add(createHiddenBottleForm());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void convertInput() {
        setConvertedInput(getModelObject());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case SELECT:
                if (event.getSource() instanceof ActionLink) {
                    BookingBottle bookingBottle = ((Item<BookingBottle>) ((ActionLink) event
                            .getSource()).getParent().getParent()).getModelObject();
                    replace((bookingBottleForm = new BookingBottleForm("bookingBottleForm",
                            new SearchFormModel(new SearchForm()), bookingBottle)).displayForm());
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case ADD:
                if (addBottle == event.getSource()) {
                    bookingBottleForm.displayForm();
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case SAVE:
                if (bookingBottleForm == event.getSource()) {
                    BookingBottle bottle = bookingBottleForm.getModelObject();
                    if (!getModelObject().contains(bottle)) {
                        bottle.setPosition(getModelObject().size());
                        getModelObject().add(bottle);
                    }
                    replace(createHiddenBottleForm());
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case CANCEL:
                if (bookingBottleForm.isCancelButton(event.getSource())) {
                    replace(createHiddenBottleForm());
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

    private Component createHiddenBottleForm() {
        BookingBottle bookingBottle = new BookingBottle();
        bookingBottle.setMax(0);
        bookingBottle.setUrl("http://www.cave-et-terroirs.fr/");
        return (bookingBottleForm = new BookingBottleForm("bookingBottleForm", new SearchFormModel(
                new SearchForm()), bookingBottle)).hideForm();
    }

}