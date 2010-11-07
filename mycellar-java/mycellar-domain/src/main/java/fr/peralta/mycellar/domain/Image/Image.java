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
package fr.peralta.mycellar.domain.Image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.IdentifiedEntity;

/**
 * @author bperalta
 * 
 */
/**
 * @author bperalta
 * 
 */
@Entity
@Table(name = "IMAGE")
@SequenceGenerator(name = "IMAGE_ID_GENERATOR", allocationSize = 1)
public class Image extends IdentifiedEntity<Image> {
    private static final long serialVersionUID = 201011071516L;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    @Column(name = "HEIGHT")
    private int height;

    @Column(name = "WIDTH")
    private int width;

    @Column(name = "CONTENT")
    private byte[] content;

    @Id
    @GeneratedValue(generator = "IMAGE_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * Needed by hibernate.
     */
    Image() {
    }

    /**
     * @param name
     * @param contentType
     * @param height
     * @param width
     * @param content
     */
    public Image(String name, String contentType, int height, int width, byte[] content) {
        this.name = name;
        this.contentType = contentType;
        this.height = height;
        this.width = width;
        this.content = content;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(Image other) {
        boolean result;

        if (getContent() == null || other.getContent() == null) {
            result = ObjectUtils.equals(getName(), other.getName())
                    && ObjectUtils.equals(getContentType(), other.getContentType());
        } else {
            result = ObjectUtils.equals(getContent(), other.getContent());
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

}
