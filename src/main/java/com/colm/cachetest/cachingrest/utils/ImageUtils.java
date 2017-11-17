package com.colm.cachetest.cachingrest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static String obtainHashOfByeArray (byte[] byteArray) {
        String hashString = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(byteArray);
            hashString = new BigInteger(1, digest).toString(16);
        }
        catch (Exception e) {
            log.error("Error getting hash of byte array", e);
        }
        return hashString;
    }

    public static boolean verifyMultipartFileIsImage (MultipartFile file) {
        BufferedImage bufferedImage = null;
        try {
            byte[] imageByters = file.getBytes();
            InputStream myInputStream = new ByteArrayInputStream(imageByters);
            bufferedImage = ImageIO.read(myInputStream);
        }
        catch (Exception e) {
            log.error("Cannot be converted to image", e);
        }
        return bufferedImage != null;
    }
}
