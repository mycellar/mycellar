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
package fr.peralta.mycellar.interfaces.client.web.components.shared.autocomplete;

import java.io.StringWriter;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.ComponentModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.util.string.Strings;

import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.resources.js.JavaScriptReferences;

/**
 * @author speralta
 */
public abstract class AbstractTypeaheadComponent<T> extends FormComponentPanel<T> {

    private static final long serialVersionUID = 201211091622L;

    private static class TextFieldModel<T> extends ComponentModel<String> {

        private static final long serialVersionUID = 201211121756L;

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        protected String getObject(Component component) {
            AbstractTypeaheadComponent<T> typeahead = (AbstractTypeaheadComponent<T>) component
                    .getParent();
            T object = typeahead.getModelObject();
            if (object == null) {
                return null;
            } else {
                return typeahead.getChoiceRenderer().getDisplayValue(object).toString();
            }
        }

    }

    private static class HiddenFieldModel<T> extends ComponentModel<String> {

        private static final long serialVersionUID = 201211121756L;

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        protected String getObject(Component component) {
            AbstractTypeaheadComponent<T> typeahead = (AbstractTypeaheadComponent<T>) component
                    .getParent();
            T object = typeahead.getModelObject();
            if (object == null) {
                return null;
            } else {
                return typeahead.getChoiceRenderer().getIdValue(object, -1);
            }
        }

    }

    private HiddenField<String> hiddenField;

    private TextField<String> textField;

    private IChoiceRenderer<T> choiceRenderer;

    private AbstractDefaultAjaxBehavior updateAjax;

    private AbstractAjaxBehavior listAjax;

    private String term;

    /**
     * @param id
     */
    public AbstractTypeaheadComponent(String id) {
        super(id);
    }

    /**
     * @param id
     * @param model
     */
    public AbstractTypeaheadComponent(String id, IModel<T> model) {
        super(id, model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forReference(JavaScriptReferences.getTypeaheadkeyJs()));
        response.render(OnDomReadyHeaderItem.forScript("$('#" + textField.getMarkupId()
                + "').typeaheadkey({sourceUrl: '" + listAjax.getCallbackUrl() + "', hiddenid: '"
                + hiddenField.getMarkupId() + "', updateUrl: '" + updateAjax.getCallbackUrl()
                + "'});"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(textField = new TextField<String>("textField", new TextFieldModel<T>()));
        add(hiddenField = new HiddenField<String>("hiddenField", new HiddenFieldModel<T>()));
        add(updateAjax = new AbstractDefaultAjaxBehavior() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void respond(AjaxRequestTarget target) {
                final String hiddenInput = hiddenField.getInput();
                final String fieldInput = textField.getInput();
                hiddenField.setConvertedInput(hiddenInput);
                textField.setConvertedInput(fieldInput);
                validate();
                if (isValid()) {
                    updateModel();
                    onUpdate(target);
                }
            }
        });
        add(listAjax = new AbstractAjaxBehavior() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onRequest() {
                term = getComponent().getRequest().getQueryParameters().getParameterValue("term")
                        .toString();
                StringWriter sw = new StringWriter();
                sw.append("[");
                if (!Strings.isEmpty(term)) {
                    Integer index = 0;
                    for (T obj : getChoices(term)) {
                        sw.append("{\"id\": \"").append(getChoiceRenderer().getIdValue(obj, index))
                                .append("\", \"val\": \"")
                                .append(getChoiceRenderer().getDisplayValue(obj).toString())
                                .append("\" },");
                        index++;
                    }
                    if (sw.getBuffer().length() > 1) {
                        sw.getBuffer().deleteCharAt(sw.getBuffer().length() - 1);
                    }
                }
                sw.append("]");
                RequestCycle.get().scheduleRequestHandlerAfterCurrent(
                        new TextRequestHandler("application/json", "utf-8", sw.toString()));
            }
        });
    }

    @Override
    protected final void convertInput() {
        String valueId = hiddenField.getConvertedInput();

        if (valueId == null) {
            setConvertedInput(null);
        } else {
            final List<? extends T> choices = getChoices(term);
            boolean found = false;
            for (int index = 0; index < choices.size(); index++) {
                // Get next choice
                final T choice = choices.get(index);
                final String idValue = choiceRenderer.getIdValue(choice, index + 1);
                if (idValue.equals(valueId)) {
                    setConvertedInput(choice);
                    found = true;
                    break;
                }
            }
            if (!found) {
                setConvertedInput(null);
            }
        }
    }

    /**
     * @param term
     * @return
     */
    protected abstract List<T> getChoices(String term);

    /**
     * @return The IChoiceRenderer used for rendering the data objects
     */
    public final IChoiceRenderer<T> getChoiceRenderer() {
        return choiceRenderer;
    }

    /**
     * Set the choice renderer to be used.
     * 
     * @param renderer
     * @return this for chaining
     */
    public final AbstractTypeaheadComponent<T> setChoiceRenderer(IChoiceRenderer<T> choiceRenderer) {
        this.choiceRenderer = choiceRenderer;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    protected void onUpdate(AjaxRequestTarget target) {
        send(getParent(), Broadcast.BUBBLE, Action.SELECT);
    }

}
