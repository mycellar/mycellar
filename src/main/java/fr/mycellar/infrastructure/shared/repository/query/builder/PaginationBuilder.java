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

import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;

/**
 * @author speralta
 */
public class PaginationBuilder<FROM> extends AbstractBuilder<SearchBuilder<FROM>> {

    private int maxResults;
    private int firstResult;

    public PaginationBuilder(SearchBuilder<FROM> searchParameters) {
        super(searchParameters);
        maxResults = -1;
        firstResult = 0;
    }

    public PaginationBuilder(SearchBuilder<FROM> searchParameters, int maxResults, int firstResult) {
        super(searchParameters);
        this.maxResults = maxResults;
        this.firstResult = firstResult;
    }

    public PaginationBuilder<FROM> firstResult(int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    public PaginationBuilder<FROM> maxResult(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

}
