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

    // finish the batch
    @PutMapping(value = "/batch/{batchId}")
    @CrossOrigin(origins = "*")
    public CacheTestingBatch completeBatch(@PathVariable Long batchId) {
       return batchService.completeBatch(batchId);
    }

    // see if the image is in Cache
    @PostMapping(value = "/checkcache/{batchId}")
    @CrossOrigin(origins = "*")
    public ClassifiedImage checkCache(@PathVariable Long batchId, @RequestBody MultipartFile file) {
        return classificationService.checkCache(batchId, file);
    }

    // For classifying the image with performance measurement
    @PostMapping(value = "/classify/{batchId}")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@PathVariable Long batchId, @RequestBody MultipartFile file) {
        return classificationService.classifyImageWithCachePerformanceMeasurements(batchId, file);
    }

    // For classifying the image with no performance measurement
    @PostMapping(value = "/classify")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@RequestBody MultipartFile file) {
        return classificationService.classifyImage(file);
    }
}
