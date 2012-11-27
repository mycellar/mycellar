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
package fr.peralta.mycellar.interfaces.client.web.components.shared;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * @author speralta
 */
public class CompoundPropertyPanel<O> extends FormComponentPanel<O> {

    private static final long serialVersionUID = 201111181310L;

    private boolean valuedAtStart;

    /**
     * @param id
     */
    public CompoundPropertyPanel(String id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected IModel<?> initModel() {
        IModel<O> superModel = (IModel<O>) super.initModel();
        if (superModel != null) {
            return new CompoundPropertyModel<O>(superModel);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBeforeRender() {
        // initialize model for children
        getModel();
        super.onBeforeRender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (getModelObject() == null) {
            valuedAtStart = false;
            O defaultObject = createDefaultObject();
            if (defaultObject != null) {
                setModelObject(defaultObject);
            }
        } else {
            valuedAtStart = true;
        }
    }

    /**
     * @return
     */
    protected O createDefaultObject() {
        return null;
    }

    /**
     * @return the valuedAtStart
     */
    protected boolean isValuedAtStart() {
        return valuedAtStart;
    }

}
