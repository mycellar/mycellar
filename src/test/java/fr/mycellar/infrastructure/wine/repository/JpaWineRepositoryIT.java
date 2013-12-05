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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context-infrastructure-test.xml" })
@Transactional
public class JpaWineRepositoryIT {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaWineRepository jpaWineRepository;

    @Test
    @Rollback
    public void like() {
        assertThat(jpaWineRepository.getWinesLike("saint", new SearchParameters()) //
                .size(), equalTo(2));

        assertThat(jpaWineRepository.getWinesLike("emilion", new SearchParameters()) //
                .size(), equalTo(1));

        assertThat(jpaWineRepository.getWinesLike("2005", new SearchParameters()) //
                .size(), equalTo(3));

        assertThat(jpaWineRepository.getWinesLike("", new SearchParameters()) //
                .size(), equalTo(4));
    }
}