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

import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.TermSelector;

/**
 * @author speralta
 */
public class FullTextBuilder<FROM> extends AbstractBuilder<FROM> {

    private final List<TermSelector<FROM>> terms;

    public FullTextBuilder(SearchBuilder<FROM> searchParameters) {
        super(searchParameters);
        terms = new ArrayList<>();
    }

    public FullTextBuilder(SearchBuilder<FROM> searchParameters, List<TermSelector<FROM>> terms) {
        super(searchParameters);
        this.terms = new ArrayList<>(terms);
    }

    public TermSelectorBuilder<FROM> on(SingularAttribute<? super FROM, String> attribute) {
        return new TermSelectorBuilder<FROM>(this, attribute);
    }

    public List<TermSelector<FROM>> getTerms() {
        return Collections.unmodifiableList(terms);
    }

    SearchBuilder<FROM> add(TermSelector<FROM> termSelector) {
        terms.add(termSelector);
        return toSearchParameters();
    }

}
