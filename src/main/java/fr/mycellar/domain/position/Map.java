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
package fr.mycellar.domain.position;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import fr.mycellar.domain.image.Image;

/**
 * @author bperalta
 */
@Embeddable
public class Map implements Serializable {

    private static final long serialVersionUID = 201011071647L;

    @OneToOne
    @JoinColumn(name = "IMAGE")
    private Image image;

    @Embedded
    private Position position;

    public Image getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
