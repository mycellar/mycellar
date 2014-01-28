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
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.MyCellarApplication;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class JpaWineRepositoryIT {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaWineRepository jpaWineRepository;

    @Test
    @Rollback
    public void like() {
        assertThat(getWinesLikeSize("saint"), equalTo(2));

        assertThat(getWinesLikeSize("emilion"), equalTo(1));

        assertThat(getWinesLikeSize("2005"), equalTo(4));

        assertThat(getWinesLikeSize(""), equalTo(0));

        assertThat(getWinesLikeSize("gaill"), equalTo(1));

        assertThat(getWinesLikeSize("renaissa"), equalTo(1));

        assertThat(getWinesLikeSize("gail renaissa"), equalTo(1));

        assertThat(getWinesLikeSize("graylac regnaisoce"), equalTo(1));
    }

    private int getWinesLikeSize(String like) {
        return jpaWineRepository.getWinesLike(like, new SearchParameters()).size();
    }
}
