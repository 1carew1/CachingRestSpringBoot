package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CacheTestingBatch;
import com.colm.cachetest.cachingrest.model.ClassifiedImage;
import com.colm.cachetest.cachingrest.service.CacheTestingBatchService;
import com.colm.cachetest.cachingrest.service.ClassificationCacheManipulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class CachingRestController {

    private static final Logger log = LoggerFactory.getLogger(CachingRestController.class);
    private static final String UNKNOWN = "UNKNOWN";

    @Autowired
    private ClassificationCacheManipulationService classificationService;
    @Autowired
    private CacheTestingBatchService batchService;

    @Value("${spring.cache.type:UNKNOWN}")
    private String cacheType;

    @Value("${memcached.cache.mode:UNKNOWN}")
    private String memcached;

    // create a batch
    @PostMapping(value = "/batch")
    @CrossOrigin(origins = "*")
    public CacheTestingBatch createBatch(@RequestBody(required = false) String setupComment) {
        String type = cacheType;
        if (!memcached.equals(UNKNOWN)) {
            type = "memcached";
        }
        log.info("Creating Batch");
        return batchService.createBatch(type, setupComment);
    }

    // see if the image is in Cache
    @PostMapping(value = "/checkcache/{batchId}")
    @CrossOrigin(origins = "*")
    public ClassifiedImage checkCache(@PathVariable Long batchId, @RequestBody MultipartFile file) throws IOException {
        log.info("Pulling Image classification from cache, batch : {}", batchId);
        return classificationService.checkCache(batchId, file);
    }

    // For classifying the image with performance measurement
    @PostMapping(value = "/classify/{batchId}")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@PathVariable Long batchId, @RequestBody MultipartFile file) throws IOException {
        log.info("Classifying Image with Cache performance metrics, batch : {}", batchId);
        return classificationService.classifyImageWithCachePerformanceMeasurements(batchId, file);
    }

    // For classifying the image with no performance measurement
    @PostMapping(value = "/classify")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@RequestBody MultipartFile file) throws IOException {
        log.info("Classifying Image without Cache performance metrics");
        return classificationService.classifyImage(file);
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
