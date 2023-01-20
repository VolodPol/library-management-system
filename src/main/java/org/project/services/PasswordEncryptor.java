package org.project.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    public static String encrypts(String exposed) {
        String encrypted = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(exposed.getBytes());

            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            encrypted = s.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /* Display the unencrypted and encrypted passwords. */
        System.out.println("Plain-text password: " + exposed);
        System.out.println("Encrypted password using MD5: " + encrypted);
        return encrypted;
    }
}
