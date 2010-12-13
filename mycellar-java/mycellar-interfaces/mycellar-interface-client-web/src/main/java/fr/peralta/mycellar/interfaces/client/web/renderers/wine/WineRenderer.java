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

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import fr.peralta.mycellar.interfaces.client.web.renderers.shared.AbstractRenderer;
import fr.peralta.mycellar.interfaces.facades.wine.Wine;

/**
 * @author bperalta
 */
@Service
public class WineRenderer extends AbstractRenderer<Wine> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(Wine wine) {
        StringBuilder result = new StringBuilder();
        if (wine != null) {
            result.append(wine.getAppellation().getRegion().getCountry().getName())
                    .append(DEFAULT_SEP).append(wine.getAppellation().getRegion().getName())
                    .append(DEFAULT_SEP).append(wine.getAppellation().getName())
                    .append(DEFAULT_SEP);
            if (StringUtils.hasText(wine.getName())) {
                result.append(wine.getName()).append(DEFAULT_SEP);
            }
            result.append(wine.getColor());
            if (wine.getVintage() != null) {
                result.append(DEFAULT_SEP).append(wine.getVintage());
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
