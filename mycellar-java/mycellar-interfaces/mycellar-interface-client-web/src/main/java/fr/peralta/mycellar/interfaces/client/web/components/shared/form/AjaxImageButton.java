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

import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.resource.LocalizedImageResource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author speralta
 */
public abstract class AjaxImageButton extends AjaxButton implements IResourceListener {

    private static final long serialVersionUID = 201111181915L;

    /** The image resource this image component references */
    private final LocalizedImageResource localizedImageResource = new LocalizedImageResource(this);

    /**
     * Constructs an image button from an image <code>ResourceReference</code>.
     * That resource reference will bind its resource to the current
     * SharedResources.
     * 
     * If you are using non sticky session clustering and the resource reference
     * is pointing to a <code>Resource</code> that isn't guaranteed to be on
     * every server, for example a dynamic image or resources that aren't added
     * with a <code>IInitializer</code> at application startup. Then if only
     * that resource is requested from another server, without the rendering of
     * the page, the image won't be there and will result in a broken link.
     * 
     * @param id
     *            See Component
     * @param resourceReference
     *            The shared image resource
     */
    public AjaxImageButton(final String id, final ResourceReference resourceReference) {
        this(id, resourceReference, null);
    }

    /**
     * Constructs an image button from an image <code>ResourceReference</code>.
     * That resource reference will bind its resource to the current
     * SharedResources.
     * 
     * If you are using non sticky session clustering and the resource reference
     * is pointing to a <code>Resource</code> that isn't guaranteed to be on
     * every server, for example a dynamic image or resources that aren't added
     * with a <code>IInitializer</code> at application startup. Then if only
     * that resource is requested from another server, without the rendering of
     * the page, the image won't be there and will result in a broken link.
     * 
     * @param id
     *            See Component
     * @param resourceReference
     *            The shared image resource
     * @param resourceParameters
     *            The resource parameters
     */
    public AjaxImageButton(final String id, final ResourceReference resourceReference,
            PageParameters resourceParameters) {
        super(id);
        setImageResourceReference(resourceReference, resourceParameters);
    }

    /**
     * Constructs an image directly from an image resource.
     * 
     * This one doesn't have the 'non sticky session clustering' problem that
     * the <code>ResourceReference</code> constructor has. But this will result
     * in a non 'stable' url and the url will have request parameters.
     * 
     * @param id
     *            See Component
     * 
     * @param imageResource
     *            The image resource
     */
    public AjaxImageButton(final String id, final IResource imageResource) {
        super(id);
        setImageResource(imageResource);
    }

    /**
     * @see org.apache.wicket.IResourceListener#onResourceRequested()
     */
    @Override
    public void onResourceRequested() {
        localizedImageResource.onResourceRequested(null);
    }

    /**
     * @param imageResource
     *            The new ImageResource to set.
     */
    public void setImageResource(final IResource imageResource) {
        localizedImageResource.setResource(imageResource);
    }

    /**
     * @param resourceReference
     *            The shared ImageResource to set.
     */
    public void setImageResourceReference(final ResourceReference resourceReference) {
        localizedImageResource.setResourceReference(resourceReference);
    }

    /**
     * @param resourceReference
     *            The shared ImageResource to set.
     * @param parameters
     *            Set the resource parameters for the resource.
     */
    public void setImageResourceReference(final ResourceReference resourceReference,
            final PageParameters parameters) {
        localizedImageResource.setResourceReference(resourceReference, parameters);
    }

    /**
     * @see org.apache.wicket.Component#setDefaultModel(org.apache.wicket.model.IModel)
     */
    @Override
    public AjaxImageButton setDefaultModel(IModel<?> model) {
        // Null out the image resource, so we reload it (otherwise we'll be
        // stuck with the old model.
        localizedImageResource.setResourceReference(null);
        localizedImageResource.setResource(null);
        return (AjaxImageButton) super.setDefaultModel(model);
    }

    /**
     * @return Resource returned from subclass
     */
    protected IResource getImageResource() {
        return localizedImageResource.getResource();
    }

    /**
     * @return ResourceReference returned from subclass
     */
    protected ResourceReference getImageResourceReference() {
        return localizedImageResource.getResourceReference();
    }

    /**
     * Processes the component tag.
     * 
     * @param tag
     *            Tag to modify
     * @see org.apache.wicket.Component#onComponentTag(ComponentTag)
     */
    @Override
    protected final void onComponentTag(final ComponentTag tag) {
        checkComponentTag(tag, "input");
        checkComponentTagAttribute(tag, "type", "image");

        final IResource resource = getImageResource();
        if (resource != null) {
            localizedImageResource.setResource(resource);
        }
        final ResourceReference resourceReference = getImageResourceReference();
        if (resourceReference != null) {
            localizedImageResource.setResourceReference(resourceReference);
        }
        localizedImageResource.setSrcAttribute(tag);
        super.onComponentTag(tag);
    }

    /**
     * @see org.apache.wicket.markup.html.form.Button#getStatelessHint()
     */
    @Override
    protected boolean getStatelessHint() {
        return (getImageResource() == null) && localizedImageResource.isStateless();
    }
}
