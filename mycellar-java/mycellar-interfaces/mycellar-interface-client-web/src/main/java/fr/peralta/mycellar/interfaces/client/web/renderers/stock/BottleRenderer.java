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
package fr.peralta.mycellar.interfaces.client.web.renderers.stock;

import org.springframework.stereotype.Service;

import fr.peralta.mycellar.domain.stock.Bottle;
import fr.peralta.mycellar.domain.wine.Format;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.AbstractRenderer;

/**
 * @author bperalta
 */
@Service
public class BottleRenderer extends AbstractRenderer<Bottle> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(Bottle bottle) {
        StringBuilder result = new StringBuilder();
        if (bottle != null) {
            Wine wine = bottle.getWine();
            if (wine != null) {
                result.append(getRendererServiceFacade().render(wine));
            } else {
                result.append(NULL_OBJECT);
            }
            if (result.length() > 0) {
                result.append(DEFAULT_SEP);
            }
            Format format = bottle.getFormat();
            if (format != null) {
                result.append(getRendererServiceFacade().render(format));
            } else {
                result.append(NULL_OBJECT);
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
    protected Class<Bottle> getRenderedClass() {
        return Bottle.class;
    }

}
