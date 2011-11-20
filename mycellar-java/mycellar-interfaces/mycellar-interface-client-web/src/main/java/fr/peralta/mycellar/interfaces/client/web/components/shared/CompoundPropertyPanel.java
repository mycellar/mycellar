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

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * @author speralta
 */
public class CompoundPropertyPanel<O> extends Panel {

    private static final long serialVersionUID = 201111181310L;

    /**
     * @param id
     */
    public CompoundPropertyPanel(String id) {
        super(id);
    }

    /**
     * Gets model.
     * 
     * @return model
     */
    @SuppressWarnings("unchecked")
    public final IModel<? extends O> getModel() {
        return (IModel<? extends O>) getDefaultModel();
    }

    /**
     * Gets model object.
     * 
     * @return model object
     */
    @SuppressWarnings("unchecked")
    public final O getModelObject() {
        return (O) getDefaultModelObject();
    }

    /**
     * Sets model.
     * 
     * @param model
     *            the model
     */
    public final void setModel(IModel<? extends O> model) {
        setDefaultModel(model);
    }

    /**
     * Sets model object.
     * 
     * @param object
     *            the model object
     */
    public final void setModelObject(O object) {
        setDefaultModelObject(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected IModel<?> initModel() {
        return new CompoundPropertyModel<O>((IModel<O>) super.initModel());
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
            O defaultObject = createDefaultObject();
            if (defaultObject != null) {
                setModelObject(defaultObject);
            }
        }
    }

    /**
     * @return
     */
    protected O createDefaultObject() {
        return null;
    }

}
