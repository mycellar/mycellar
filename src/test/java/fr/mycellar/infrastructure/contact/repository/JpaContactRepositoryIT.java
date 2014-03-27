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
package fr.mycellar.infrastructure.contact.repository;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.MyCellarApplication;
import fr.mycellar.domain.contact.Contact;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MyCellarApplication.class })
@ActiveProfiles("test")
@Transactional
public class JpaContactRepositoryIT {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JpaContactRepository jpaContactRepository;

    @Test
    @Rollback
    public void getAllToContact_test() {
        Contact contact = new Contact();
        contact.setCurrent(new LocalDate().minusDays(3));
        contact.setNext(new LocalDate().plusDays(3));
        contact.setProducer(entityManager.find(Producer.class, 1));
        contact.setText("Text");
        jpaContactRepository.save(contact);
        contact = new Contact();
        contact.setCurrent(new LocalDate().minusDays(3));
        contact.setNext(new LocalDate().minusDays(1));
        contact.setProducer(entityManager.find(Producer.class, 2));
        contact.setText("Text");
        contact = jpaContactRepository.save(contact);

        assertEquals(Arrays.asList(contact), jpaContactRepository.getAllToContact());
    }

    @Test
    @Rollback
    public void countLastContacts_test() {
        initLastContactsTest();

        assertEquals(1, jpaContactRepository.countLastContacts(new SearchParameters()));
    }

    @Test
    @Rollback
    public void getLastContacts_test() {
        Contact contact = initLastContactsTest();

        assertEquals(Arrays.asList(contact), jpaContactRepository.getLastContacts(new SearchParameters()));
    }

    private Contact initLastContactsTest() {
        Contact contact = new Contact();
        contact.setCurrent(new LocalDate().minusDays(3));
        contact.setNext(new LocalDate().minusDays(1));
        contact.setProducer(entityManager.find(Producer.class, 1));
        contact.setText("Text");
        contact = jpaContactRepository.save(contact);
        contact = new Contact();
        contact.setCurrent(new LocalDate().minusDays(1));
        contact.setNext(new LocalDate().plusDays(2));
        contact.setProducer(entityManager.find(Producer.class, 1));
        contact.setText("Text");
        contact = jpaContactRepository.save(contact);
        return contact;
    }
}
