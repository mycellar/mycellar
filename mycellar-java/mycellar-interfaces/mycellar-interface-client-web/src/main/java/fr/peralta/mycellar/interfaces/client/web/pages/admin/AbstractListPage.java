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
package fr.peralta.mycellar.interfaces.client.web.pages.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.AbstractEntityOrder;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.EditColumn;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public abstract class AbstractListPage<E extends IdentifiedEntity, OE, O extends AbstractEntityOrder<OE, O>>
        extends AdminSuperPage {

    private static final long serialVersionUID = 201203262241L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractListPage.class);

    /**
     * @param parameters
     */
    public AbstractListPage(PageParameters parameters) {
        super(parameters);
        add(new AdvancedTable<E>("list", getColumnsPlusEdit(), getDataProvider(), 50));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new NavPanel("listMenu", getClass(), getDescriptorServiceFacade().getListPages()));
    }

    /**
     * @return
     */
    private List<IColumn<E>> getColumnsPlusEdit() {
        List<IColumn<E>> columns = new ArrayList<IColumn<E>>(getColumns());
        columns.add(new EditColumn<E>());
        return columns;
    }

    /**
     * @return
     */
    protected abstract MultipleSortableDataProvider<E, OE, O> getDataProvider();

    /**
     * @return
     */
    protected abstract List<IColumn<E>> getColumns();

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
                    E object = ((E) ((ActionLink) event.getSource()).getParent()
                            .getDefaultModelObject());
                    setResponsePage(getEditPageClass(), getEditPageParameters(object));
                    event.stop();
                }
                break;
            case ADD:
                if (event.getSource() instanceof ActionLink) {
                    setResponsePage(getEditPageClass(), getEditPageParameters(null));
                    event.stop();
                }
                break;
            case DELETE:
                if (event.getSource() instanceof ActionLink) {
                    E object = ((E) ((ActionLink) event.getSource()).getParent()
                            .getDefaultModelObject());
                    // TODO delete object
                    event.stop();
                }
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

    /**
     * @param object
     * @return
     */
    protected abstract PageParameters getEditPageParameters(E object);

    /**
     * @return
     */
    protected abstract Class<? extends AbstractEditPage<E>> getEditPageClass();

}
