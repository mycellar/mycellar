/*
 * Copyright 2014, MyCellar
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
package fr.mycellar.infrastructure.shared.repository.query.builder.property;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.query.PropertySelectors;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.builder.AbstractBuilder;

/**
 * @author speralta
 */
public class DisjunctionPropertySelectorsBuilder<FROM, PARENT> extends AbstractBuilder<FROM> {

    private final PropertySelectors<FROM> propertySelectors = new PropertySelectors<>();

    private final PARENT parent;

    public DisjunctionPropertySelectorsBuilder(SearchBuilder<FROM> searchParameters, PARENT parent) {
        super(searchParameters);
        this.parent = parent;
    }

    public <TO> DisjunctionPropertySelectorBuilder<FROM, FROM, TO, PARENT> property(SingularAttribute<? super FROM, TO> attribute) {
        return new DisjunctionPropertySelectorBuilder<>(this, attribute);
    }

    public <TO> DisjunctionPropertySelectorBuilder<FROM, FROM, TO, PARENT> property(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new DisjunctionPropertySelectorBuilder<>(this, attribute);
    }

    public <TO> DisjunctionPropertySelectorsBuilder<FROM, PARENT> property(PropertySelector<FROM, TO> propertySelector) {
        propertySelectors.property(propertySelector);
        return this;
    }

    public ConjunctionPropertySelectorsBuilder<FROM, DisjunctionPropertySelectorsBuilder<FROM, PARENT>> conjunction() {
        ConjunctionPropertySelectorsBuilder<FROM, DisjunctionPropertySelectorsBuilder<FROM, PARENT>> conjunction = new ConjunctionPropertySelectorsBuilder<FROM, DisjunctionPropertySelectorsBuilder<FROM, PARENT>>(
                toSearchParameters(), this);
        propertySelectors.add(conjunction.getPropertySelectors());
        return conjunction;
    }

    public PropertySelectors<FROM> getPropertySelectors() {
        return propertySelectors;
    }

    <TO> DisjunctionPropertySelectorsBuilder<FROM, PARENT> add(Path<FROM, TO> path, @SuppressWarnings("unchecked") TO... values) {
        propertySelectors.property(new PropertySelector<>(path, values));
        return this;
    }

    public PARENT end() {
        return parent;
    }
}
