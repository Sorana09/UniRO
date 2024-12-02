package com.example.personalizedLearningPlatform.crypt;

import org.springframework.util.DigestUtils;

public class MD5 {
    public static String getMD5(String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes()).toUpperCase();
    }
}
