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
package fr.peralta.mycellar.interfaces.client.web.components.shared.form;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.LocalDate;

import fr.peralta.mycellar.interfaces.client.web.converters.LocalDateConverter;
import fr.peralta.mycellar.interfaces.client.web.resources.css.CssReferences;
import fr.peralta.mycellar.interfaces.client.web.resources.js.JavaScriptReferences;

/**
 * A TextField that is mapped to a <code>org.joda.time.LocalDate</code> object.
 * 
 * If no date pattern is explicitly specified, the default
 * <code>DateFormat.SHORT</code> pattern for the current locale will be used.
 * 
 * @author speralta
 */
public class LocalDateTextField extends TextField<LocalDate> implements ITextFormatProvider {

    private static final long serialVersionUID = 201203071827L;

    /**
     * The converter for the TextField
     */
    private final IConverter<LocalDate> converter;

    /**
     * Creates a new LocalDateTextField.
     * 
     * @param id
     *            The id of the text field
     * 
     * @see org.apache.wicket.markup.html.form.TextField
     */
    public LocalDateTextField(final String id) {
        this(id, null);
    }

    /**
     * Creates a new LocalDateTextField.
     * 
     * @param id
     *            The id of the text field
     * @param model
     *            The model
     * @see org.apache.wicket.markup.html.form.TextField
     */
    public LocalDateTextField(final String id, final IModel<LocalDate> model) {
        super(id, model, LocalDate.class);
        converter = new LocalDateConverter();
    }

    /**
     * Returns the default converter if created without pattern; otherwise it
     * returns a pattern-specific converter.
     * 
     * @param type
     *            The type for which the convertor should work
     * 
     * @return A pattern-specific converter
     * 
     * @see org.apache.wicket.markup.html.form.TextField
     */
    @SuppressWarnings("unchecked")
    @Override
    public <C> IConverter<C> getConverter(final Class<C> type) {
        if (LocalDate.class.isAssignableFrom(type)) {
            return (IConverter<C>) converter;
        } else {
            return super.getConverter(type);
        }
    }

    /**
     * Returns the date pattern.
     * 
     * @see org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider#getTextFormat()
     */
    @Override
    public String getTextFormat() {
        return "dd/MM/YYYY";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getInputType() {
        return "date";
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);

        response.render(CssHeaderItem.forReference(CssReferences.getBootstrapDatePickerCss()));
        response.render(JavaScriptHeaderItem.forReference(JavaScriptReferences
                .getBootstrapDatePickerJs()));
        response.render(OnDomReadyHeaderItem.forScript("$('#" + getMarkupId()
                + "').datepicker({format: 'dd/mm/yyyy', weekstart: '2'});"));
    }

}
