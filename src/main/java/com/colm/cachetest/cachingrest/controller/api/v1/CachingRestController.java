package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CubedInfo;
import com.colm.cachetest.cachingrest.model.FactorialInfo;
import com.colm.cachetest.cachingrest.model.LabelWithProbability;
import com.colm.cachetest.cachingrest.service.ClassifyImageService;
import com.colm.cachetest.cachingrest.service.CubedCacheService;
import com.colm.cachetest.cachingrest.service.FactorialCacheService;
import com.colm.cachetest.cachingrest.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping ("/api/v1")
public class CachingRestController {

    private static final Logger log = LoggerFactory.getLogger(CachingRestController.class);

    @Autowired
    private CubedCacheService cubedCacheService;
    @Autowired
    private FactorialCacheService factorialCacheService;
    @Autowired
    private ClassifyImageService classifyImageService;

    @RequestMapping (value = "/cube/{someNumber}", method = RequestMethod.GET)
    public CubedInfo doSomeComplexMaths (@PathVariable Long someNumber) {
        log.info("Cubing : " + someNumber);
        CubedInfo cubedInfo = cubedCacheService.getCubedInfo(someNumber);
        log.info(someNumber + "^3 = " + cubedInfo.getNumberCubed().toString());
        return cubedInfo;
    }

    @RequestMapping (value = "/factorial/{someNumber}", method = RequestMethod.GET)
    public FactorialInfo computerTheFactorial (@PathVariable int someNumber) {
        log.info("Getting Factorial of : " + someNumber);
        FactorialInfo factorialInfo = factorialCacheService.computerFactorial(someNumber);
        log.info(someNumber + "! = " + factorialInfo.getFactorial().toString());
        return factorialInfo;
    }

    @PostMapping (value = "/classify")
    @CrossOrigin(origins = "*")
    public LabelWithProbability classifyImage(@RequestParam MultipartFile file) throws IOException, NoSuchAlgorithmException {
        checkImageContents(file);

        byte[] uploadBytes = file.getBytes();

        String hashOfImage = ImageUtils.obtainHashOfByeArray(uploadBytes);
        log.info("Classifying Image of Hash : " + hashOfImage);
        return classifyImageService.classifyImage(uploadBytes, hashOfImage);
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    private void checkImageContents(MultipartFile file) {
//        MagicMatch match;
//        try {
//            match = Magic.getMagicMatch(file.getBytes());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        String mimeType = match.getMimeType();
//        if (!mimeType.startsWith("image")) {
//            throw new IllegalArgumentException("Not an image type: " + mimeType);
//        }
    }
}
