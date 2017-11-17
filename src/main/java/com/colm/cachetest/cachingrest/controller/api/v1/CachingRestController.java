package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CubedInfo;
import com.colm.cachetest.cachingrest.model.FactorialInfo;
import com.colm.cachetest.cachingrest.model.LabelWithProbability;
import com.colm.cachetest.cachingrest.service.ClassifyImageService;
import com.colm.cachetest.cachingrest.service.CubedCacheService;
import com.colm.cachetest.cachingrest.service.FactorialCacheService;
import com.colm.cachetest.cachingrest.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
    @CrossOrigin (origins = "*")
    public LabelWithProbability classifyImage (@RequestParam MultipartFile file) throws IOException {
        boolean validImage = ImageUtils.verifyMultipartFileIsImage(file);
        LabelWithProbability labelWithProbability = new LabelWithProbability("Unsupported Image Type", 100);
        if (validImage) {
            byte[] uploadBytes = file.getBytes();
            String hashOfImage = ImageUtils.obtainHashOfByeArray(uploadBytes);
            log.info("Classifying Image of Hash : " + hashOfImage);
            labelWithProbability = classifyImageService.classifyImage(uploadBytes, hashOfImage);
            log.info("Classidied The image");
        }
        return labelWithProbability;
    }

    @RequestMapping (value = "/")
    public String index () {
        return "index";
    }
}
