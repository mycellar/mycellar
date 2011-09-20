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
package fr.peralta.mycellar.interfaces.client.web.components.wine.cloud;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.wine.Producer;
import fr.peralta.mycellar.domain.wine.WineColorEnum;
import fr.peralta.mycellar.domain.wine.WineTypeEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.SimpleTagCloud;
import fr.peralta.mycellar.interfaces.client.web.components.shared.cloud.TagCloudPanel;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class WineColorEnumFromProducerAndTypeTagCloud extends SimpleTagCloud<WineColorEnum> {

    private static final long serialVersionUID = 201107252130L;

    @SpringBean
    private WineServiceFacade wineServiceFacade;

    private final IModel<WineTypeEnum> wineTypeEnumModel;

    private final IModel<Producer> producerModel;

    /**
     * @param id
     * @param label
     * @param producerModel
     * @param wineTypeEnumModel
     */
    public WineColorEnumFromProducerAndTypeTagCloud(String id, IModel<String> label,
            IModel<Producer> producerModel, IModel<WineTypeEnum> wineTypeEnumModel) {
        super(id, label);
        if (producerModel == null) {
            throw new NullPointerException("Producer model cannot be null.");
        }
        if (wineTypeEnumModel == null) {
            throw new NullPointerException("WineTypeEnum model cannot be null.");
        }
        this.producerModel = producerModel;
        this.wineTypeEnumModel = wineTypeEnumModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TagCloudPanel<WineColorEnum> createTagCloudPanel(String id) {
        return new TagCloudPanel<WineColorEnum>(id,
                getListFrom(wineServiceFacade.getColorsWithCounts(wineTypeEnumModel.getObject(),
                        producerModel.getObject())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isReadyToSelect() {
        return (producerModel.getObject() != null) && (wineTypeEnumModel.getObject() != null);
    }

}
