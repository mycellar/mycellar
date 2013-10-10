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
package fr.mycellar.application.contact.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.mail.internet.MimeMessage;

import org.joda.time.LocalDate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;

import fr.mycellar.application.admin.ConfigurationService;
import fr.mycellar.application.contact.ContactService;
import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.domain.contact.Contact;
import fr.mycellar.domain.contact.Contact_;
import fr.mycellar.domain.contact.repository.ContactRepository;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.shared.repository.SearchParameters;
import fr.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
@Named
@Singleton
public class ContactServiceImpl extends AbstractSimpleService<Contact, ContactRepository> implements ContactService {

    private ConfigurationService configurationService;

    private ContactRepository contactRepository;

    private JavaMailSender javaMailSender;

    /**
     * {@inheritDoc}
     */
    @Override
    public long countLastContacts(SearchParameters searchParameters) {
        return contactRepository.countLastContacts(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Contact> getLastContacts(SearchParameters searchParameters) {
        return contactRepository.getLastContacts(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void sendReminders() {
        final StringBuilder content = new StringBuilder();
        List<Contact> contacts = contactRepository.getAllToContact();
        if ((contacts != null) && (contacts.size() > 0)) {
            for (Contact contact : contacts) {
                content.append("Domaine ").append(contact.getProducer().getName()).append(" à recontacter le ").append(contact.getNext()).append("\r\n");
                content.append("Dernier contact le ").append(contact.getCurrent()).append(" :").append("\r\n").append(contact.getText()).append("\r\n");
                content.append("------------------------------------------------").append("\r\n");
            }
            MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                    helper.setTo(configurationService.getReminderAddressReceivers());
                    helper.setFrom(configurationService.getMailAddressSender());
                    helper.setSubject("Contacts à recontacter");
                    helper.setText(content.toString());
                }
            };
            try {
                javaMailSender.send(mimeMessagePreparator);
            } catch (Exception e) {
                throw new RuntimeException("Cannot send email.", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Contact entity) throws BusinessException {
        if (entity.getProducer() == null) {
            throw new BusinessException(BusinessError.CONTACT_00002);
        }
        if (entity.getCurrent() == null) {
            throw new BusinessException(BusinessError.CONTACT_00003);
        }
        Contact existing = find(entity.getProducer(), entity.getCurrent());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.CONTACT_00001);
        }
    }

    /**
     * @param producer
     * @param current
     * @return
     */
    @Override
    public Contact find(Producer producer, LocalDate current) {
        return contactRepository.findUniqueOrNone(new SearchParametersBuilder()//
                .propertyWithValue(producer, Contact_.producer) //
                .propertyWithValue(current, Contact_.current)//
                .toSearchParameters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContactRepository getRepository() {
        return contactRepository;
    }

    /**
     * @param contactRepository
     *            the contactRepository to set
     */
    @Inject
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * @param javaMailSender
     *            the javaMailSender to set
     */
    @Inject
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * @param configurationService
     *            the configurationService to set
     */
    @Inject
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}