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
package fr.peralta.mycellar.interfaces.facades.image.mappers;

import fr.peralta.mycellar.interfaces.facades.image.Image;
import fr.peralta.mycellar.interfaces.facades.shared.mappers.AbstractMapper;

/**
 * @author speralta
 */
public class ImageDtoMapper extends AbstractMapper<fr.peralta.mycellar.domain.image.Image, Image> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Image map(fr.peralta.mycellar.domain.image.Image from) {
        Image image = new Image();
        image.setContent(from.getContent());
        image.setContentType(from.getContentType());
        image.setHeight(from.getHeight());
        image.setId(from.getId());
        image.setName(from.getName());
        image.setWidth(from.getWidth());
        return image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<fr.peralta.mycellar.domain.image.Image> getFromClass() {
        return fr.peralta.mycellar.domain.image.Image.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<Image> getToClass() {
        return Image.class;
    }

}
