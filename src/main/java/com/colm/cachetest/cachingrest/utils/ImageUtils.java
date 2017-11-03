package com.colm.cachetest.cachingrest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;

public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static String obtainHashOfByeArray(byte[] byteArray) {
        String hashString = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(byteArray);
            hashString = new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error("Error getting hash of byte array", e);
        }
        return hashString;
    }
}
