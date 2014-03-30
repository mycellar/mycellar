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

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;
import fr.mycellar.infrastructure.shared.repository.query.selector.TermSelector;

/**
 * @author speralta
 */
public class TermSelectorBuilder<FROM> {

    private final RootSelectorsBuilder<FROM> rootSelectorsBuilder;
    private final TermSelector<FROM> termSelector;

    public TermSelectorBuilder(RootSelectorsBuilder<FROM> selectorsBuilder, Path<FROM, String> path) {
        this.rootSelectorsBuilder = selectorsBuilder;
        termSelector = new TermSelector<FROM>(path);
    }

    public TermSelectorBuilder<FROM> searchSimilarity(Integer searchSimilarity) {
        termSelector.setSearchSimilarity(searchSimilarity);
        return this;
    }

    public SearchBuilder<FROM> search(String... selected) {
        termSelector.selected(selected);
        return rootSelectorsBuilder.add(termSelector).toParent();
    }

}
