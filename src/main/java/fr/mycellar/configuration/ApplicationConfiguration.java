/*
 * Copyright 2014, MyCellar
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
package fr.mycellar.configuration;

import javax.inject.Inject;
import javax.mail.Session;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.digest.StringDigester;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author speralta
 */
@Configuration
public class ApplicationConfiguration {

    @Inject
    private Session session;

    @Bean
    public StringDigester stringDigester() {
        PooledStringDigester stringDigester = new PooledStringDigester();
        stringDigester.setPoolSize(4);
        stringDigester.setAlgorithm("SHA-1");
        stringDigester.setIterations(10000);
        stringDigester.setSaltSizeBytes(8);
        stringDigester.setSaltGenerator(new RandomSaltGenerator());
        return stringDigester;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setSession(session);
        return javaMailSender;
    }

}
