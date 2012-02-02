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
package fr.peralta.mycellar.interfaces.client.web.components.shared.img;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author speralta
 */
public class ImageReferences {

    public static ResourceReference getAddImage() {
        return getImage("add.png");
    }

    public static ResourceReference getCancelImage() {
        return getImage("cancel.png");
    }

    public static ResourceReference getRemoveImage() {
        return getImage("delete.png");
    }

    public static ResourceReference getDisconnectImage() {
        return getImage("disconnect.png");
    }

    public static ResourceReference getDatabaseImage() {
        return getImage("database.png");
    }

    public static ResourceReference getEyeImage() {
        return getImage("eye.png");
    }

    public static ResourceReference getPagingFirstImage() {
        return getImage("resultset_first.png");
    }

    public static ResourceReference getPagingPrevImage() {
        return getImage("resultset_previous.png");
    }

    public static ResourceReference getPagingNextImage() {
        return getImage("resultset_next.png");
    }

    public static ResourceReference getPagingLastImage() {
        return getImage("resultset_last.png");
    }

    public static ResourceReference getBulletArrowDownImage() {
        return getImage("bullet_arrow_down.png");
    }

    public static ResourceReference getBulletArrowUpImage() {
        return getImage("bullet_arrow_up.png");
    }

    public static ResourceReference getBulletArrowUpDownImage() {
        return getImage("bullet_arrow_up_down.png");
    }

    public static ResourceReference getPencilImage() {
        return getImage("pencil.png");
    }

    private static ResourceReference getImage(String filename) {
        return new PackageResourceReference(ImageReferences.class, filename);
    }

    /**
     * Refuse instanciation.
     */
    private ImageReferences() {
        throw new UnsupportedOperationException();
    }

}
