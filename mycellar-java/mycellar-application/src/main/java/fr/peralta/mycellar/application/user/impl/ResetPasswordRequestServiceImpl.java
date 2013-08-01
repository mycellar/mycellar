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
package fr.peralta.mycellar.application.user.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.mail.internet.MimeMessage;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.joda.time.LocalDateTime;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;

import fr.peralta.mycellar.application.admin.ConfigurationService;
import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.user.ResetPasswordRequestService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.peralta.mycellar.domain.user.ResetPasswordRequest;
import fr.peralta.mycellar.domain.user.ResetPasswordRequest_;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.ResetPasswordRequestRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class ResetPasswordRequestServiceImpl extends AbstractSimpleService<ResetPasswordRequest, ResetPasswordRequestRepository> implements ResetPasswordRequestService {

    private static SecureRandom secureRandom = new SecureRandom();

    private ResetPasswordRequestRepository resetPasswordRequestRepository;
    private ConfigurationService configurationService;
    private JavaMailSender javaMailSender;

    /**
     * {@inheritDoc}
     */
    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanOldRequests() {
        resetPasswordRequestRepository.deleteOldRequests();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAndSendEmail(User user, String url) {
        // Create request
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setDateTime(new LocalDateTime());
        request.setKey(new String(Base64.encodeBase64(secureRandom.generateSeed(128), false)).substring(0, 32));
        request.setUser(user);
        // Merge it in repository
        resetPasswordRequestRepository.save(request);
        // Send email to email
        final String email = user.getEmail();
        final String address;
        try {
            address = url + "?key=" + URLEncoder.encode(request.getKey(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported.", e);
        }

        MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setTo(email);
                helper.setFrom(configurationService.getMailAddressSender());
                helper.setSubject("Changement de mot de passe");
                helper.setText("Allez à l'adresse suivante : " + address);
            }
        };
        try {
            javaMailSender.send(mimeMessagePreparator);
        } catch (Exception e) {
            throw new RuntimeException("Cannot send email.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResetPasswordRequest getByKey(String key) {
        ResetPasswordRequest request = resetPasswordRequestRepository.findUniqueOrNone( //
                new SearchParametersBuilder() //
                        .propertyWithValue(key, ResetPasswordRequest_.key) //
                        .toSearchParameters());
        return (request != null) && request.getDateTime().isAfter(new LocalDateTime().minusHours(1)) ? request : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllForUser(User user) {
        resetPasswordRequestRepository.deleteAllForUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(ResetPasswordRequest entity) throws BusinessException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResetPasswordRequestRepository getRepository() {
        return resetPasswordRequestRepository;
    }

    /**
     * @param resetPasswordRequestRepository
     *            the resetPasswordRequestRepository to set
     */
    @Inject
    public void setResetPasswordRequestRepository(ResetPasswordRequestRepository resetPasswordRequestRepository) {
        this.resetPasswordRequestRepository = resetPasswordRequestRepository;
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
