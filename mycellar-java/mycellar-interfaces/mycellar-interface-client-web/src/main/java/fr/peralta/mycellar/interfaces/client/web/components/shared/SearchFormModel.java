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
package fr.peralta.mycellar.interfaces.client.web.components.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentInheritedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;

/**
 * @author speralta
 */
public class SearchFormModel implements IComponentInheritedModel<SearchForm> {

    private static final long serialVersionUID = 201109261601L;

    private SearchForm searchForm;

    /**
     * Constructor
     * 
     * @param searchForm
     *            The searchForm
     */
    public SearchFormModel(final SearchForm searchForm) {
        this.searchForm = searchForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchForm getObject() {
        return searchForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObject(SearchForm searchForm) {
        this.searchForm = searchForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detach() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <W> IWrapModel<W> wrapOnInheritance(Component component) {
        return new AttachedSearchFormModel<W>(component);
    }

    private class AttachedSearchFormModel<T> implements IWrapModel<T> {

        private static final long serialVersionUID = 201109261605L;

        private final FilterEnum filter;

        /**
         * @param owner
         */
        public AttachedSearchFormModel(Component owner) {
            this.filter = getFilterFromComponentId(owner.getId());
        }

        /**
         * @param id
         * @return
         */
        private FilterEnum getFilterFromComponentId(String id) {
            if ("countries".equals(id) || "country".equals(id)) {
                return FilterEnum.COUNTRY;
            } else if ("regions".equals(id) || "region".equals(id)) {
                return FilterEnum.REGION;
            } else if ("appellations".equals(id) || "appellation".equals(id)) {
                return FilterEnum.APPELLATION;
            } else if ("cellars".equals(id) || "cellar".equals(id)) {
                return FilterEnum.CELLAR;
            } else if ("types".equals(id) || "type".equals(id)) {
                return FilterEnum.TYPE;
            } else if ("colors".equals(id) || "color".equals(id)) {
                return FilterEnum.COLOR;
            } else if ("formats".equals(id) || "format".equals(id)) {
                return FilterEnum.FORMAT;
            } else if ("producers".equals(id) || "producer".equals(id)) {
                return FilterEnum.PRODUCER;
            } else {
                return null;
            }
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public T getObject() {
            if (filter == null) {
                return null;
            }
            Set<?> set = searchForm.getSet(filter);
            if (set == null) {
                return (T) new ArrayList<Object>();
            } else {
                return (T) new ArrayList<Object>(searchForm.getSet(filter));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setObject(T object) {
            if (filter != null) {
                Set<?> set = searchForm.getSet(filter);
                if (set != null) {
                    set.clear();
                }
                searchForm.addAllToSet(filter, (List<?>) object);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void detach() {
            SearchFormModel.this.detach();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public IModel<?> getWrappedModel() {
            return SearchFormModel.this;
        }

    }

}