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
package fr.mycellar.infrastructure.admin.repository;

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
import fr.mycellar.domain.admin.Configuration;
import fr.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.mycellar.domain.admin.Configuration_;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class JpaConfigurationRepositoryIT {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaConfigurationRepository jpaConfigurationRepository;

    private static final int NB_CONFIGURATIONS = 2;

    @Test
    @Rollback
    public void all() {
        assertThat(jpaConfigurationRepository.find(new SearchParameters<Configuration>()).size(), equalTo(NB_CONFIGURATIONS));
        assertThat(jpaConfigurationRepository.findCount(new SearchParameters<Configuration>()), equalTo(Long.valueOf(NB_CONFIGURATIONS)));
    }

    @Test
    @Rollback
    public void byPropertySelector() {
        assertThat(jpaConfigurationRepository.find( //
                new SearchParameters<Configuration>() //
                        .property(Configuration_.key).equalsTo(ConfigurationKeyEnum.MAIL_ADDRESS_SENDER)) //
                .size(), equalTo(1));
    }

}
