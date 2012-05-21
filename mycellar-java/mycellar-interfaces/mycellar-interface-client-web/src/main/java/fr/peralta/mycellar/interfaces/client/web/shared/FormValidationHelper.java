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
package fr.peralta.mycellar.interfaces.client.web.shared;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.validation.ValidationError;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;

/**
 * @author speralta
 * 
 */
public class FormValidationHelper {

    /**
     * @param form
     * @param e
     */
    public static void error(Form<?> form, final BusinessException e) {
        FormComponent<?> formComponent = form
                .visitFormComponents(new IVisitor<FormComponent<?>, FormComponent<?>>() {
                    @Override
                    public void component(FormComponent<?> object, IVisit<FormComponent<?>> visit) {
                        if (object.getId().equals(e.getBusinessError().getProperty())) {
                            visit.stop(object);
                        }
                    }
                });
        if (formComponent != null) {
            formComponent.error(new ValidationError().addMessageKey(e.getBusinessError().getKey()));
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("label", form.getString(e.getBusinessError().getProperty()));
            form.error(form.getString(e.getBusinessError().getKey()), map);
        }
    }

    /**
     * Refuse instantiation.
     */
    private FormValidationHelper() {
        throw new IllegalStateException(FormValidationHelper.class.getSimpleName()
                + " must not be instantiated.");
    }

}
