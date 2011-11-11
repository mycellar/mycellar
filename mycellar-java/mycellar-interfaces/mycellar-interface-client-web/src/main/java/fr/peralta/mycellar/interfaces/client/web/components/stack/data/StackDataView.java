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
package fr.peralta.mycellar.interfaces.client.web.components.stack.data;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataViewBase;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.interfaces.client.web.components.shared.img.ImageReferences;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.StackPage;

/**
 * @author speralta
 */
public class StackDataView extends DataViewBase<Stack> {

    private static final long serialVersionUID = 201111081449L;

    /**
     * @param id
     * @param searchFormModel
     */
    public StackDataView(String id, IModel<SearchForm> searchFormModel) {
        super(id, new StackDataProvider(searchFormModel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(Item<Stack> item) {
        item.setModel(new CompoundPropertyModel<Stack>(item.getModel()));
        item.add(new Label("hashCode"));
        int firstLineIndex = item.getModelObject().getStack().indexOf("\n");
        item.add(new Label("content", item.getModelObject().getStack().substring(0, firstLineIndex)));
        item.add(new Label("count"));
        item.add(new WebMarkupContainer("detail").add(new BookmarkablePageLink<Void>("detailStack",
                StackPage.class, StackPage.getPageParameters(item.getModelObject().getId()))
                .add(new Image("detailStackImg", ImageReferences.getEyeImage()))));
    }

}
