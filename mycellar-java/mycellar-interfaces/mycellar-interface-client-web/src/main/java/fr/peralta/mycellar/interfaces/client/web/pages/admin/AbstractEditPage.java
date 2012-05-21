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

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.nav.NavPanel;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public abstract class AbstractEditPage<E extends IdentifiedEntity> extends AdminSuperPage {

    private static final long serialVersionUID = 201203270838L;

    private static final Logger logger = LoggerFactory.getLogger(AbstractEditPage.class);

    protected static final String NEW_PARAMETER_VALUE = "new";

    private final ObjectForm<E> objectForm;

    private final SearchFormModel searchFormModel;

    /**
     * @param parameters
     */
    public AbstractEditPage(PageParameters parameters) {
        super(parameters);
        searchFormModel = new SearchFormModel(new SearchForm());
        objectForm = getObjectForm("form", searchFormModel, getObject(parameters));
        add(objectForm.displayForm());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onInitialize() {
        add(new NavPanel("listMenu", getClass(), getClass(), getDescriptorServiceFacade()
                .getListPages()));
        super.onInitialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case CANCEL:
                if (objectForm.isCancelButton(event.getSource())) {
                    setResponsePage(getListPageClass());
                    AjaxTool.ajaxReRender(this);
                    event.stop();
                }
                break;
            case SAVE:
                if (objectForm == event.getSource()) {
                    E object = objectForm.getModelObject();
                    saveObject(object);
                    if (getFeedbackMessages().size() > 0) {
                        AjaxTool.ajaxReRender(this);
                    } else {
                        setResponsePage(getListPageClass());
                    }
                    event.stop();
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

    /**
     * @param parameters
     * @return
     */
    protected E getObject(PageParameters pageParameters) {
        E object = null;
        StringValue objectIdParameter = pageParameters.get(getIdParameterName());
        if ((objectIdParameter != null) && !objectIdParameter.isEmpty()) {
            if (NEW_PARAMETER_VALUE.equals(objectIdParameter.toString())) {
                object = createNewObject();
            } else {
                object = getObjectById(objectIdParameter.toInteger());
            }
        }
        if (object == null) {
            throw new WicketRuntimeException("Wrong page parameters.");
        }
        return object;
    }

    /**
     * @param objectId
     * @return
     */
    protected abstract E getObjectById(Integer objectId);

    /**
     * @return
     */
    protected abstract E createNewObject();

    /**
     * @return
     */
    protected abstract String getIdParameterName();

    /**
     * @param object
     * @return true if ok
     */
    protected abstract void saveObject(E object);

    /**
     * @param id
     * @param searchFormModel
     * @param object
     * @return
     */
    protected abstract ObjectForm<E> getObjectForm(String id, IModel<SearchForm> searchFormModel,
            E object);

    /**
     * @return
     */
    protected abstract Class<? extends AbstractListPage<E, ?, ?>> getListPageClass();

}
