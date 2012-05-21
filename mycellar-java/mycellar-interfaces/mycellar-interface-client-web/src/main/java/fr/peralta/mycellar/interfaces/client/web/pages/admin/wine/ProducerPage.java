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
package fr.peralta.mycellar.interfaces.client.web.pages.admin.wine;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.ValidationError;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.form.ObjectForm;
import fr.peralta.mycellar.interfaces.client.web.components.wine.form.ProducerForm;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractEditPage;
import fr.peralta.mycellar.interfaces.client.web.pages.admin.AbstractListPage;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class ProducerPage extends AbstractEditPage<Producer> {

    private static final long serialVersionUID = 201203270918L;

    private static final String PRODUCER_ID_PARAMETER = "producer";

    /**
     * @param producer
     * @return
     */
    public static PageParameters getPageParameters(Producer producer) {
        return new PageParameters().add(PRODUCER_ID_PARAMETER, producer.getId());
    }

    /**
     * @return
     */
    public static PageParameters getPageParametersForCreation() {
        return new PageParameters().add(PRODUCER_ID_PARAMETER, NEW_PARAMETER_VALUE);
    }

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    /**
     * @param parameters
     */
    public ProducerPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Producer getObjectById(Integer objectId) {
        return wineServiceFacade.getProducerById(objectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Producer createNewObject() {
        return new Producer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIdParameterName() {
        return PRODUCER_ID_PARAMETER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveObject(Producer object) {
        try {
            wineServiceFacade.saveProducer(object);
        } catch (BusinessException e) {
            get(e.getBusinessError().getProperty()).get(e.getBusinessError().getProperty()).error(
                    new ValidationError().addMessageKey(e.getBusinessError().getKey()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ObjectForm<Producer> getObjectForm(String id, IModel<SearchForm> searchFormModel,
            Producer object) {
        return new ProducerForm(id, searchFormModel, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends AbstractListPage<Producer, ?, ?>> getListPageClass() {
        return ProducersPage.class;
    }

}
