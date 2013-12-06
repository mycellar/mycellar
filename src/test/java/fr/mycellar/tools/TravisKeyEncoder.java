/*
 * Copyright 2013, MyCellar
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

import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * @author speralta
 */
public class TravisKeyEncoder {

    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4GIgqXdPOkehPwITfExrH+NrT" + //
            "q3uJlNS0ZWeEd/QCVLayVGTScAzYrJZcC3hw09rHe9fQv0VRtvfGN1O4Zh4Q1cTI" + //
            "1DBswD5F68SFxWN/DbPafCmu+wuCEr767iCqw8JwxynChyRYMOcdf22VQz8QUbfb" + //
            "CKwHayMOtCC8XiPLBQIDAQAB";

    public static void main(String... args) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey))));
        System.out.println(new String(Base64.encodeBase64(cipher.doFinal("".getBytes()))));
    }

}
