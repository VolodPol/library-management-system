package org.project.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The class is supposed to encrypt passwords into hashcode by
 * the MD5 MessageDigest
 */
public class PasswordEncryptor {
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
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encrypted;
    }
}
