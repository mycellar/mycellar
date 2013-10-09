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
package fr.peralta.mycellar.infrastructure.wine.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.domain.shared.NamedEntity_;
import fr.peralta.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.peralta.mycellar.domain.wine.Country;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:context-infrastructure-persistence-test.xml" })
@Transactional
public class JpaCountryRepositoryIT {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaCountryRepository jpaCountryRepository;

    @Test
    @Rollback
    public void byTermSelector() {
        Country country = new Country();
        country.setName("France");
        jpaCountryRepository.save(country);

        FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(entityManager);
        fullTextEntityManager.flushToIndexes();

        assertThat(jpaCountryRepository.find( //
                new SearchParametersBuilder() //
                        .term(NamedEntity_.name, "Fiance") //
                        .toSearchParameters()) //
                .size(), equalTo(1));
    }

}