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
package fr.peralta.mycellar.interfaces.client.web.renderers.shared;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.springframework.stereotype.Component;

/**
 * @author speralta
 */
@Component
public class AmountRenderer extends AbstractRenderer<Float> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(Float object) {
        return new DecimalFormat("###,##0.00", new DecimalFormatSymbols(Locale.FRENCH))
                .format(object) + " €";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Float> getRenderedClass() {
        return Float.class;
    }

}
