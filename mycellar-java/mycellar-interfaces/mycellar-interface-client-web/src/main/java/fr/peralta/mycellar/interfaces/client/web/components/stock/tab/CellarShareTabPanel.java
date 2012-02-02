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
package fr.peralta.mycellar.interfaces.client.web.components.stock.tab;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.stock.Cellar;
import fr.peralta.mycellar.domain.stock.CellarShare;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.AdvancedTable;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.tab.TabAdvancedTablePanel;
import fr.peralta.mycellar.interfaces.client.web.components.stock.data.CellarShareDataView;
import fr.peralta.mycellar.interfaces.client.web.components.stock.edit.CellarShareEditPanel;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingUtils;
import fr.peralta.mycellar.interfaces.facades.stock.StockServiceFacade;

/**
 * @author speralta
 */
public class CellarShareTabPanel extends TabAdvancedTablePanel<CellarShare> {

    private static final long serialVersionUID = 201201241804L;
    private static final Logger logger = LoggerFactory.getLogger(CellarShareTabPanel.class);
    private static final String CELLAR_SHARE_COMPONENT_ID = "cellarShare";

    @SpringBean
    private StockServiceFacade stockServiceFacade;

    /**
     * @param id
     * @param cellarModel
     */
    public CellarShareTabPanel(String id, IModel<Cellar> cellarModel) {
        super(id, cellarModel);
        setOutputMarkupId(true);
        add(createHiddenCellarShareForm());
    }

    @SuppressWarnings("unchecked")
    public IModel<Cellar> getModel() {
        return (IModel<Cellar>) getDefaultModel();
    }

    public Cellar getModelObject() {
        return (Cellar) getDefaultModelObject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AdvancedTable<CellarShare> createAdvancedTable(String tableId) {
        return new CellarShareDataView(tableId, new SearchFormModel(new SearchForm().addToSet(
                FilterEnum.CELLAR, getModelObject())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingUtils.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            IEventSource source = event.getSource();
            switch (action) {
            case ADD:
                displayCellarShareForm();
                break;
            case DELETE:
                if (source instanceof ActionLink) {
                    CellarShare cellarShare = (CellarShare) ((ActionLink) source).getParent()
                            .getDefaultModelObject();
                    stockServiceFacade.deleteCellarShare(cellarShare);
                } else {
                    throw new WicketRuntimeException("Wrong source.");
                }
                break;
            case SAVE:
                CellarShare cellarShareToSave = (CellarShare) get(CELLAR_SHARE_COMPONENT_ID)
                        .getDefaultModelObject();
                cellarShareToSave.setCellar(getModelObject());
                stockServiceFacade.saveCellarShare(cellarShareToSave);
                replace(createHiddenCellarShareForm());
                break;
            case SELECT:
                if (source instanceof ActionLink) {
                    CellarShare cellarShare = (CellarShare) ((ActionLink) source).getParent()
                            .getDefaultModelObject();
                    replace(createHiddenCellarShareForm(cellarShare));
                    displayCellarShareForm();
                } else {
                    throw new WicketRuntimeException("Wrong source.");
                }
                break;
            case CANCEL:
                replace(createHiddenCellarShareForm());
                break;
            case MODEL_CHANGED:
                break;
            default:
                throw new WicketRuntimeException("Action " + action + " not managed.");
            }
            event.stop();
            AjaxTool.ajaxReRender(this);
        }
        LoggingUtils.logEventProcessed(logger, event);
    }

    /**
     * @return
     */
    private Component createHiddenCellarShareForm() {
        return createHiddenCellarShareForm(new CellarShare());
    }

    /**
     * @return
     */
    private Component createHiddenCellarShareForm(CellarShare cellarShare) {
        return new ObjectForm<CellarShare>(CELLAR_SHARE_COMPONENT_ID, cellarShare).replace(
                new CellarShareEditPanel(ObjectForm.EDIT_PANEL_COMPONENT_ID)).setVisibilityAllowed(
                false);
    }

    /**
     * @return
     */
    private Component displayCellarShareForm() {
        return get(CELLAR_SHARE_COMPONENT_ID).setVisibilityAllowed(true);
    }

}
