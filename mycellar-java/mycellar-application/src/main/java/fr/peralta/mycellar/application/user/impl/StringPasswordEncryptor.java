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

import org.jasypt.digest.StringDigester;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author speralta
 */
@Component
public class StringPasswordEncryptor implements PasswordEncryptor {

    private StringDigester stringDigester;

    /**
     * {@inheritDoc}
     */
    @Override
    public String encryptPassword(String password) {
        return stringDigester.digest(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return stringDigester.matches(plainPassword, encryptedPassword);
    }

    /**
     * @param stringDigester
     *            the stringDigester to set
     */
    @Autowired
    public void setStringDigester(StringDigester stringDigester) {
        this.stringDigester = stringDigester;
    }

}
