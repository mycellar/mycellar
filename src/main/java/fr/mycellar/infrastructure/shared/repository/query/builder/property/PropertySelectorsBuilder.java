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
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.shared.repository.query.builder.AbstractBuilder;

/**
 * @author speralta
 */
public class PropertySelectorsBuilder<FROM> extends AbstractBuilder<FROM> {
    private static final long serialVersionUID = 201403271745L;

    private final PropertySelectors<FROM> propertySelectors = new PropertySelectors<>();

    public PropertySelectorsBuilder(SearchParameters<FROM> searchParameters) {
        super(searchParameters);
    }

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(SingularAttribute<? super FROM, TO> attribute) {
        return new PropertySelectorBuilder<>(this, attribute);
    }

    public <TO> PropertySelectorBuilder<FROM, FROM, TO> property(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new PropertySelectorBuilder<>(this, attribute);
    }

    public <TO> PropertySelectorsBuilder<FROM> property(PropertySelector<FROM, TO> propertySelector) {
        propertySelectors.property(propertySelector);
        return this;
    }

    public DisjunctionPropertySelectorsBuilder<FROM, SearchParameters<FROM>> disjunction() {
        DisjunctionPropertySelectorsBuilder<FROM, SearchParameters<FROM>> disjunction = new DisjunctionPropertySelectorsBuilder<FROM, SearchParameters<FROM>>(toSearchParameters(),
                toSearchParameters());
        propertySelectors.add(disjunction.getPropertySelectors().or());
        return disjunction;
    }

    public PropertySelectors<FROM> getPropertySelectors() {
        return propertySelectors;
    }

    <TO> SearchParameters<FROM> add(Path<FROM, TO> path, @SuppressWarnings("unchecked") TO... values) {
        propertySelectors.property(new PropertySelector<>(path, values));
        return toSearchParameters();
    }
}
