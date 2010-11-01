package fr.peralta.mycellar.domain.wine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.ObjectUtils;

import fr.peralta.mycellar.domain.shared.NamedEntity;
import fr.peralta.mycellar.domain.shared.ValidationPattern;

@Entity
@Table(name = "REGION", uniqueConstraints = @UniqueConstraint(columnNames = {
        "NAME", "COUNTRY" }))
@AttributeOverride(name = "name", column = @Column(name = "NAME", nullable = false))
@SequenceGenerator(name = "REGION_ID_GENERATOR", allocationSize = 1)
public class Region extends NamedEntity<Region> {

    private static final long serialVersionUID = 201010311741L;

    @Pattern(regexp = ValidationPattern.URL_PATTERN)
    @Column(name = "MAP_URL")
    private String mapUrl;

    @Column(name = "DESCRIPTION")
    private String description;

    @Valid
    @ManyToOne
    @JoinColumn(name = "COUNTRY", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "region")
    private final Set<Appellation> appellations = new HashSet<Appellation>();

    @Id
    @GeneratedValue(generator = "REGION_ID_GENERATOR")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    /**
     * @param name
     * @param country
     * @param mapUrl
     * @param description
     */
    public Region(String name, Country country, String mapUrl,
            String description) {
        super(name);
        this.country = country;
        this.mapUrl = mapUrl;
        this.description = description;
    }

    /**
     * Needed by Hibernate.
     */
    Region() {
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the mapUrl
     */
    public String getMapUrl() {
        return mapUrl;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @return the appellations
     */
    public Set<Appellation> getAppellations() {
        return Collections.unmodifiableSet(appellations);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.peralta.mycellar.domain.shared.IdentifiedEntity#getHashCodeData()
     */
    @Override
    protected Object[] getHashCodeData() {
        return new Object[] { getName() };
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.peralta.mycellar.domain.shared.IdentifiedEntity#dataEquals(fr.peralta
     * .mycellar.domain.shared.IdentifiedEntity)
     */
    @Override
    protected boolean dataEquals(Region other) {
        return ObjectUtils.equals(getName(), other.getName())
                && ObjectUtils.equals(getCountry(), other.getCountry());
    }

}
