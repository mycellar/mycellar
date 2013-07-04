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
package fr.peralta.mycellar.application.contact.impl;

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

import fr.peralta.mycellar.application.admin.ConfigurationService;
import fr.peralta.mycellar.application.contact.ContactService;
import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.domain.contact.Contact;
import fr.peralta.mycellar.domain.contact.Contact_;
import fr.peralta.mycellar.domain.contact.repository.ContactRepository;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.EntitySelector;
import fr.peralta.mycellar.domain.shared.repository.PropertySelector;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.wine.Producer;

/**
 * @author speralta
 */
@Named
@Singleton
public class ContactServiceImpl extends AbstractSimpleService<Contact, ContactRepository> implements
        ContactService {

    private ConfigurationService configurationService;

    private ContactRepository contactRepository;

    private JavaMailSender javaMailSender;

    /**
     * {@inheritDoc}
     */
    @Override
    public long countLastContacts() {
        return contactRepository.countLastContacts();
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
                content.append("Domaine ").append(contact.getProducer().getName())
                        .append(" à recontacter le ").append(contact.getNext()).append("\r\n");
                content.append("Dernier contact le ").append(contact.getCurrent()).append(" :")
                        .append("\r\n").append(contact.getText()).append("\r\n");
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
        Contact existing = find(entity.getProducer(), entity.getCurrent());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
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
        return contactRepository.findUnique(new SearchParameters().entity(
                EntitySelector.newEntitySelector(Contact_.producer, producer)).property(
                PropertySelector.newPropertySelector(current, Contact_.current)));
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
