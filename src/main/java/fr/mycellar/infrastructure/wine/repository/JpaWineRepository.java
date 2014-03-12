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
package fr.mycellar.infrastructure.wine.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;

import fr.mycellar.domain.shared.NamedEntity_;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Appellation_;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.Country_;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Producer_;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.Region_;
import fr.mycellar.domain.wine.Wine;
import fr.mycellar.domain.wine.Wine_;
import fr.mycellar.infrastructure.shared.repository.HibernateSearchUtil;
import fr.mycellar.infrastructure.shared.repository.JpaSimpleRepository;
import fr.mycellar.infrastructure.shared.repository.PropertySelector;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@Named
@Singleton
public class JpaWineRepository extends JpaSimpleRepository<Wine> implements WineRepository {

    private HibernateSearchUtil hibernateSearchUtil;

    /**
     * Default constructor.
     */
    public JpaWineRepository() {
        super(Wine.class);
    }

    @Override
    public long countWinesLike(String input, SearchParameters searchParameters) {
        restrictFromHibernateSearch(input, searchParameters);
        return findCount(searchParameters);
    }

    @Override
    public List<Wine> getWinesLike(String input, SearchParameters searchParameters) {
        restrictFromHibernateSearch(input, searchParameters);
        if (searchParameters.hasProperties()) {
            return find(searchParameters);
        } else {
            return new ArrayList<Wine>();
        }
    }

    private void restrictFromHibernateSearch(String input, SearchParameters searchParameters) {
        restrictFromHibernateSearch(searchParameters, Country.class, NamedEntity_.name, input, //
                Wine_.appellation, Appellation_.region, Region_.country, Country_.id);
        restrictFromHibernateSearch(searchParameters, Region.class, NamedEntity_.name, input, //
                Wine_.appellation, Appellation_.region, Region_.id);
        restrictFromHibernateSearch(searchParameters, Appellation.class, NamedEntity_.name, input, //
                Wine_.appellation, Appellation_.id);
        restrictFromHibernateSearch(searchParameters, Producer.class, NamedEntity_.name, input, //
                Wine_.producer, Producer_.id);
        restrictFromHibernateSearch(searchParameters, Wine.class, NamedEntity_.name, input, //
                Wine_.id);
        Scanner vintageScanner = new Scanner(input);
        String vintageString = vintageScanner.findInLine("[0-9]{4}");
        if (StringUtils.isNotBlank(vintageString)) {
            Integer vintage = Integer.parseInt(vintageString);
            searchParameters.property(Wine_.vintage, vintage);
        }
        vintageScanner.close();
    }

    private <X> void restrictFromHibernateSearch(SearchParameters searchParameters, Class<X> from, SingularAttribute<? super X, String> attribute, String input, Attribute<?, ?>... attributes) {
        SearchParameters fullTextSearchParameters = new SearchParameters().term(attribute, input).searchSimilarity(null);
        List<Serializable> ids = hibernateSearchUtil.findId(from, fullTextSearchParameters);
        if ((ids == null) || ids.isEmpty()) {
            ids = hibernateSearchUtil.findId(from, fullTextSearchParameters.searchSimilarity(1));
        }
        if ((ids == null) || ids.isEmpty()) {
            ids = hibernateSearchUtil.findId(from, fullTextSearchParameters.searchSimilarity(2));
        }
        if ((ids != null) && (ids.size() > 0)) {
            List<Integer> realIds = new ArrayList<>();
            for (Serializable id : ids) {
                realIds.add((Integer) id);
            }
            PropertySelector<Wine, Integer> selector = new PropertySelector<>(attributes);
            selector.setSelected(realIds);
            searchParameters.property(selector);
        }
    }

    // BEANS METHODS

    @Inject
    public void setHibernateSearchUtil(HibernateSearchUtil hibernateSearchUtil) {
        this.hibernateSearchUtil = hibernateSearchUtil;
    }

}
