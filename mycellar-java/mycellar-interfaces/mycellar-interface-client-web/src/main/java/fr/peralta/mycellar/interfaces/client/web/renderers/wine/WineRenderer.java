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
package fr.peralta.mycellar.interfaces.client.web.renderers.wine;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.AbstractRenderer;

/**
 * @author bperalta
 */
@Service
public class WineRenderer extends AbstractRenderer<Wine> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(Wine object) {
        StringBuilder result = new StringBuilder();
        if (object != null) {
            result.append(getRendererServiceFacade().render(object.getProducer()))
                    .append(DEFAULT_SEP)
                    .append(getRendererServiceFacade().render(object.getAppellation()))
                    .append(DEFAULT_SEP);
            if (StringUtils.isNotEmpty(object.getName())) {
                result.append(object.getName()).append(DEFAULT_SEP);
            }
            result.append(getRendererServiceFacade().render(object.getColor())).append(DEFAULT_SEP);
            if (object.getVintage() != null) {
                result.append(object.getVintage());
            } else {
                result.append("Non millésimé");
            }
        } else {
            result.append(NULL_OBJECT);
        }
        return result.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Wine> getRenderedClass() {
        return Wine.class;
    }

}
