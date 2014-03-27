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
package fr.mycellar.infrastructure.shared.repository.query.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;
import fr.mycellar.infrastructure.shared.repository.query.TermSelector;

/**
 * @author speralta
 */
public class FullTextBuilder<FROM> extends AbstractBuilder<FROM> {
    private static final long serialVersionUID = 201403271745L;

    private final List<TermSelector> terms = new ArrayList<>();
    private Integer searchSimilarity = 2;

    public FullTextBuilder(SearchParameters<FROM> searchParameters) {
        super(searchParameters);
    }

    public FullTextBuilder<FROM> searchSimilarity(Integer searchSimilarity) {
        this.searchSimilarity = searchSimilarity;
        return this;
    }

    public <TO> TermSelectorBuilder<FROM, TO> on(SingularAttribute<? super FROM, TO> attribute) {
        return new TermSelectorBuilder<FROM, TO>(this, attribute);
    }

    public List<TermSelector> getTerms() {
        return Collections.unmodifiableList(terms);
    }

    public Integer getSearchSimilarity() {
        return searchSimilarity;
    }

    SearchParameters<FROM> add(TermSelector termSelector) {
        terms.add(termSelector);
        return toSearchParameters();
    }

}
