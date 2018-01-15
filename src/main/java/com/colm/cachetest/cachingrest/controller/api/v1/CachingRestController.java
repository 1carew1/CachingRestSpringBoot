package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.model.LabelWithProbability;
import com.colm.cachetest.cachingrest.repository.CacherPerformanceRepository;
import com.colm.cachetest.cachingrest.service.ClassifyImageService;
import com.colm.cachetest.cachingrest.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping ("/api/v1")
public class CachingRestController {

    private static final Logger log = LoggerFactory.getLogger(CachingRestController.class);
    @Autowired
    private CacherPerformanceRepository cacherPerformanceRepository;

    @Autowired
    private ClassifyImageService classifyImageService;

    @PostMapping (value = "/classify")
    @CrossOrigin (origins = "*")
    public LabelWithProbability classifyImage (@RequestBody MultipartFile file) throws IOException {
        boolean validImage = ImageUtils.verifyMultipartFileIsImage(file);
        LabelWithProbability labelWithProbability = new LabelWithProbability("Unsupported Image Type", 100);
        if (validImage) {
            byte[] uploadBytes = file.getBytes();
            String imageHash = ImageUtils.obtainHashOfByeArray(uploadBytes);
            // Get time to pull from Cache
            Date startDate = new Date();
            labelWithProbability = classifyImageService.checkIfInCache(imageHash);
            Date endDate = new Date();
            boolean cacheHit = false;
            if (labelWithProbability != null) {
                cacheHit = true;
            }
            else {
                classifyImageService.evictFromCache(imageHash);
                log.info("Classifying Image of Hash : {}", imageHash);
                // Get time to process Image
                startDate = new Date();
                labelWithProbability = classifyImageService.classifyImage(uploadBytes, imageHash);
                endDate = new Date();
            }
            CachePerformance cachePerformance = new CachePerformance(startDate, endDate, imageHash, cacheHit);
            // Some service that will store this
            cacherPerformanceRepository.save(cachePerformance);
        }
        return labelWithProbability;
    }

    @RequestMapping (value = "/")
    public String index () {
        return "index";
    }
}
