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
package fr.peralta.mycellar.infrastructure.admin.repository;

import static fr.peralta.mycellar.domain.shared.repository.PropertySelector.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.domain.admin.ConfigurationKeyEnum;
import fr.peralta.mycellar.domain.admin.Configuration_;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:context-infrastructure-persistence-test.xml" })
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
        assertThat(jpaConfigurationRepository.find(new SearchParameters()).size(), equalTo(NB_CONFIGURATIONS));
        assertThat(jpaConfigurationRepository.findCount(new SearchParameters()), equalTo(Long.valueOf(NB_CONFIGURATIONS)));
    }

    @Test
    @Rollback
    public void byPropertySelector() {
        assertThat(jpaConfigurationRepository.find(new SearchParameters().property(newPropertySelector(ConfigurationKeyEnum.MAIL_ADDRESS_SENDER, Configuration_.key))).size(), equalTo(1));
    }

}
