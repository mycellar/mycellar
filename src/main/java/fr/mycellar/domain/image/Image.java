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
package fr.mycellar.domain.image;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;

/**
 * @author bperalta
 */
@Entity
@Table(name = "IMAGE")
@SequenceGenerator(name = "IMAGE_ID_GENERATOR", allocationSize = 1)
public class Image extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Column(name = "CONTENT")
    private byte[] content;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    @Column(name = "HEIGHT")
    private int height;

    @Id
    @GeneratedValue(generator = "IMAGE_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "WIDTH")
    private int width;

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Image image = (Image) other;
        boolean result;
        if ((getContent() == null) || (image.getContent() == null)) {
            result = Objects.equals(getName(), image.getName()) && Objects.equals(getContentType(), image.getContentType());
        } else {
            result = Objects.equals(getContent(), image.getContent());
        }
        return result;
    }

    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("contentType", contentType).append("height", height).append("width", width).build();
    }

}
