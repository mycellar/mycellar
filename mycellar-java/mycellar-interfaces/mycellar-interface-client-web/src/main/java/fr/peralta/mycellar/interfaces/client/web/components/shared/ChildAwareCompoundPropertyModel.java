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

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IComponentInheritedModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * @author speralta
 */
public class ChildAwareCompoundPropertyModel<T> implements IComponentInheritedModel<T>,
        IChainingModel<T> {
    private static final long serialVersionUID = 201108042349L;

    private Object target;

    /**
     * Constructor
     * 
     * @param model
     *            The model
     */
    public ChildAwareCompoundPropertyModel(final IModel<T> model) {
        target = model;
    }

    /**
     * Constructor
     * 
     * @param object
     *            The model object
     */
    public ChildAwareCompoundPropertyModel(final T object) {
        target = object;
    }

    /**
     * @see org.apache.wicket.model.IModel#getObject()
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        if (target instanceof IModel) {
            return ((IModel<T>) target).getObject();
        }
        return (T) target;
    }

    /**
     * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setObject(T object) {
        if (target instanceof IModel) {
            ((IModel<T>) target).setObject(object);
        } else {
            target = object;
        }
    }

    /**
     * @see org.apache.wicket.model.IChainingModel#getChainedModel()
     */
    @Override
    public IModel<?> getChainedModel() {
        if (target instanceof IModel) {
            return (IModel<?>) target;
        }
        return null;
    }

    /**
     * @see org.apache.wicket.model.IChainingModel#setChainedModel(org.apache.wicket.model.IModel)
     */
    @Override
    public void setChainedModel(IModel<?> model) {
        target = model;
    }

    /**
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    @Override
    public void detach() {
        if (target instanceof IDetachable) {
            ((IDetachable) target).detach();
        }
    }

    /**
     * @see org.apache.wicket.model.IComponentInheritedModel#wrapOnInheritance(org.apache.wicket.Component)
     */
    @Override
    public <C> IWrapModel<C> wrapOnInheritance(Component component) {
        return new AttachedChildAwareCompoundPropertyModel<C>(component, this);
    }

    /**
     * Binds this model to a special property by returning a model that has this
     * compound model as its nested/wrapped model and the property which should
     * be evaluated. This can be used if the id of the Component isn't a valid
     * property for the data object.
     * 
     * @param property
     * @return The IModel that is a wrapper around the current model and the
     *         property
     * @param <S>
     *            the type of the property
     */
    public <S> IModel<S> bind(String property) {
        return new PropertyModel<S>(this, property);
    }

    /**
     * Component aware variation of the {@link CompoundPropertyModel} that
     * components that inherit the model get.
     * 
     * @author speralta
     * @param <C>
     *            The model object type
     */
    private static class AttachedChildAwareCompoundPropertyModel<C> extends
            AbstractPropertyModel<C> implements IWrapModel<C>, IComponentInheritedModel<C> {
        private static final long serialVersionUID = 2011080472348L;

        private final Component owner;

        /**
         * Constructor
         * 
         * @param owner
         *            component that this model has been attached to
         * @param wrappedModel
         *            the wrapped model
         */
        public AttachedChildAwareCompoundPropertyModel(Component owner,
                IComponentInheritedModel<?> wrappedModel) {
            super(wrappedModel);
            this.owner = owner;
        }

        /**
         * @see org.apache.wicket.model.AbstractPropertyModel#propertyExpression()
         */
        @Override
        protected String propertyExpression() {
            return owner.getId();
        }

        /**
         * @see org.apache.wicket.model.IWrapModel#getWrappedModel()
         */
        @SuppressWarnings("unchecked")
        @Override
        public IModel<C> getWrappedModel() {
            return (IModel<C>) getChainedModel();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <W> IWrapModel<W> wrapOnInheritance(Component component) {
            return new AttachedChildAwareCompoundPropertyModel<W>(component, this);
        }

    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        AppendingStringBuffer sb = new AppendingStringBuffer().append("Model:classname=["
                + getClass().getName() + "]");
        sb.append(":nestedModel=[").append(target).append("]");
        return sb.toString();
    }
}
