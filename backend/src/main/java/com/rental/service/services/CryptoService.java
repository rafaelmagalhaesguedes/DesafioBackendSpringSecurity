package com.rental.service.services;

import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class CryptoService {
    private static final StrongTextEncryptor encryptor;
    private static final String SECRET_KEY = "${api.security.crypto.secret}";

    static {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(SECRET_KEY);
    }

    public static String encrypt(String rawText) {
        return encryptor.encrypt(rawText);
    }
    public static String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
