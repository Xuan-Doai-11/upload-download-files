package com.example.uploaddownloadfile.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class HashHelper {

    private static final String SALT = "17@HSB@&8:%)&*DHA)!(L#";

    static String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private HashHelper() {}


    public static String encrypt(String str) {
        return Base64.getEncoder().encodeToString((SALT + str).getBytes(StandardCharsets.UTF_8));
    }

    public static String encryptBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

}
