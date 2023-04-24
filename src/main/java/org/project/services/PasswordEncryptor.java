package org.project.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The class is supposed to encrypt passwords into hashcode by
 * the MD5 MessageDigest
 */
public class PasswordEncryptor {
    private static final Logger log = LoggerFactory.getLogger(PasswordEncryptor.class);
    /**
     * @param exposed the bare password
     * @return encrypted hashcode of the password
     */
    public static String encrypt(String exposed) {
        String encrypted = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(exposed.getBytes());

            byte[] bytes = m.digest();

            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            encrypted = s.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Couldn't encrypt the password", e);
        }
        return encrypted;
    }
}
