package com.company;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Formatter;

public class GenerationKeyAndHMAC {
    public static SecretKey generateStrongAESKey(final int keysize) {
        final KeyGenerator kgen;
        try {
            kgen = KeyGenerator.getInstance("AES");
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("AES key generator should always be available in a Java runtime", e);
        }
        final SecureRandom rng;
        try {
            rng = SecureRandom.getInstanceStrong();
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException("No strong secure random available to generate strong AES key", e);
        }
        // already throws IllegalParameterException for wrong key sizes
        kgen.init(keysize, rng);

        return kgen.generateKey();
    }

    private static String toHex(final byte[] data) {
        final StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    private static final String HMAC_SHA256 = "HmacSHA256";

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static String calculateHMAC(String data, String key)
            throws  NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA256);
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(secretKeySpec);
        return toHexString(mac.doFinal(data.getBytes()));
    }
    public String[] keyGenerate(String[] args,int pc) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey strongAESKey = generateStrongAESKey(128);
        String key = toHex(strongAESKey.getEncoded());
        String hmac = calculateHMAC(args[pc], key);
        return new String[]{hmac, key};
    }
}
