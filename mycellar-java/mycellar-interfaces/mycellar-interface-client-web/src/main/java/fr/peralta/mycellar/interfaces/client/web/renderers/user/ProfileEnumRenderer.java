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
package fr.peralta.mycellar.interfaces.client.web.renderers.user;

import org.springframework.stereotype.Component;

import fr.peralta.mycellar.domain.user.ProfileEnum;
import fr.peralta.mycellar.interfaces.client.web.renderers.shared.AbstractRenderer;

/**
 * @author speralta
 */
@Component
public class ProfileEnumRenderer extends AbstractRenderer<ProfileEnum> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel(ProfileEnum object) {
        String result;
        if (object == null) {
            result = NULL_OBJECT;
        } else {
            switch (object) {
            case ADMIN:
                return "Administrateur";
            case BASIC:
                return "Simple";
            case BOOKING:
                return "Réservation";
            case CELLAR:
                return "Gestion de cave";
            case MYCELLAR:
                return "Complet";
            default:
                throw new IllegalStateException("Unknown " + ProfileEnum.class.getSimpleName()
                        + " value '" + object + "'.");
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<ProfileEnum> getRenderedClass() {
        return ProfileEnum.class;
    }

}
