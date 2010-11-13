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

import fr.peralta.mycellar.interfaces.client.web.renderers.shared.Renderer;
import fr.peralta.mycellar.interfaces.client.web.renderers.wine.FormatRenderer;
import fr.peralta.mycellar.interfaces.client.web.renderers.wine.WineRenderer;
import fr.peralta.mycellar.interfaces.facades.stock.Bottle;
import fr.peralta.mycellar.interfaces.facades.wine.Format;
import fr.peralta.mycellar.interfaces.facades.wine.Wine;

/**
 * @author bperalta
 * 
 */
public class BottleRenderer implements Renderer<Bottle> {

    final static String SEP = DEFAULT_SEP;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(Bottle bottle) {
        StringBuilder result = new StringBuilder();
        if (bottle != null) {
            Wine wine = bottle.getWine();
            if (wine != null) {
                result.append(new WineRenderer().getLabel(wine));
            } else
                result.append(NULL_OBJECT);

            Format format = bottle.getFormat();
            if (format != null) {
                result.append(new FormatRenderer().getLabel(format));
            } else
                result.append(NULL_OBJECT);
        } else
            result.append(NULL_OBJECT);

        return result.toString();
    }
}
