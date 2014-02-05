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
package fr.mycellar.tools;

import org.jasypt.digest.StringDigester;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import fr.mycellar.application.user.impl.StringPasswordEncryptor;
import fr.mycellar.configuration.ApplicationConfiguration;

/**
 * @author speralta
 */
public class MyCellarPasswordGenerator {

    public static void main(String... args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        StringPasswordEncryptor encryptor = new StringPasswordEncryptor();
        encryptor.setStringDigester(context.getBean(StringDigester.class));
        String encryptedPassword = encryptor.encryptPassword("test");
        System.out.println(encryptedPassword + " size: " + encryptedPassword.length());
    }

}
