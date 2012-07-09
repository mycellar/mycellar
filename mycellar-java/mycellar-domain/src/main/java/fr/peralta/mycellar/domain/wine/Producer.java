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
package fr.peralta.mycellar.domain.wine;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.peralta.mycellar.domain.position.Address;
import fr.peralta.mycellar.domain.shared.IdentifiedEntity;
import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.shared.ValidationPattern;

/**
 * @author speralta
 */
@Entity
@Table(name = "PRODUCER")
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false))
@SequenceGenerator(name = "PRODUCER_ID_GENERATOR", allocationSize = 1)
public class Producer extends NamedEntity {

    private static final long serialVersionUID = 201111181451L;

    @Embedded
    private Address address;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTACT_INFORMATION")
    private String contactInformation;

    @Id
    @GeneratedValue(generator = "PRODUCER_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "EMAIL")
    private String email;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "WEBSITE_URL")
    private String websiteUrl;

    @SuppressWarnings("unused")
    @OneToMany(mappedBy = "producer")
    private final Set<Wine> wines = new HashSet<Wine>();

    /**
     * @return the contactInformation
     */
    public String getContactInformation() {
        return contactInformation;
    }

    /**
     * @param contactInformation
     *            the contactInformation to set
     */
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax
     *            the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the websiteUrl
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param websiteUrl
     *            the websiteUrl to set
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean dataEquals(IdentifiedEntity other) {
        Producer producer = (Producer) other;
        return ObjectUtils.equals(getName(), producer.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ToStringBuilder toStringBuilder() {
        return super.toStringBuilder().append("address", address)
                .append("description", description).append("websiteUrl", websiteUrl);
    }

}
