package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.model.CacheRemainder;
import com.colm.cachetest.cachingrest.model.CacheTestingBatch;
import com.colm.cachetest.cachingrest.model.ClassifiedImage;
import com.colm.cachetest.cachingrest.service.AsynchDBService;
import com.colm.cachetest.cachingrest.service.CacheTestingBatchService;
import com.colm.cachetest.cachingrest.service.ClassifyImageService;
import com.colm.cachetest.cachingrest.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class CachingRestController {

    private static final Logger log = LoggerFactory.getLogger(CachingRestController.class);
    private static final String UNKNOWN = "UNKNOWN";

    @Autowired
    private ClassifyImageService classifyImageService;
    @Autowired
    private AsynchDBService asynchDBService;
    @Autowired
    private CacheTestingBatchService cacheTestingBatchService;

    @Value("${spring.cache.type:UNKNOWN}")
    private String redisCache;

    @Value("${spring.hazelcast.config:UNKNOWN}")
    private String hazelCast;

    @Value("${spring.cache.ehcache.config:UNKNOWN}")
    private String ehcache;

    // create a batch
    @PostMapping(value = "/batch")
    @CrossOrigin(origins = "*")
    public CacheTestingBatch createBatch(@RequestBody(required = false) String setupComment) {
        String cacheType = "NONE";
        if (!redisCache.equals(UNKNOWN)) {
            cacheType = redisCache;
        } else if (!ehcache.equals(UNKNOWN)) {
            cacheType = "Ehcache";
        } else if (!hazelCast.equals(UNKNOWN)) {
            cacheType = "Hazelcast";
        }
        return cacheTestingBatchService.createBatch(cacheType, setupComment);
    }

    // For see if the item is in Cache
    @PostMapping(value = "/checkcache/{batchId}")
    @CrossOrigin(origins = "*")
    public String checkCache(@PathVariable Long batchId, @RequestBody MultipartFile file) throws IOException {
        boolean validImage = ImageUtils.verifyMultipartFileIsImage(file);
        CacheTestingBatch cacheTestingBatch = cacheTestingBatchService.obtainBatch(batchId);
        if (validImage && cacheTestingBatch != null) {
            byte[] uploadBytes = file.getBytes();
            String imageHash = ImageUtils.obtainHashOfByeArray(uploadBytes);
            ClassifiedImage classifiedImage = classifyImageService.checkIfInCache(imageHash);
            if (classifiedImage != null) {
                CacheRemainder cacheRemainder = new CacheRemainder(imageHash, cacheTestingBatch);
                asynchDBService.saveEntity(cacheRemainder);
                return "{result : \"Success. Item is present In Cache\"}";
            }
        }
        return "{result : \"Failure. Item is not present In Cache\"}";
    }

    // For classifying the image with performance measurement
    @PostMapping(value = "/classify/{batchId}")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@PathVariable Long batchId, @RequestBody MultipartFile file) throws IOException {
        boolean validImage = ImageUtils.verifyMultipartFileIsImage(file);
        CacheTestingBatch cacheTestingBatch = cacheTestingBatchService.obtainBatch(batchId);
        ClassifiedImage classifiedImage = new ClassifiedImage("Unsupported Image Type", 100, null);
        if (validImage && cacheTestingBatch != null) {
            Long fileSizekB = file.getSize() / 1024;
            String fileName = file.getOriginalFilename();
            byte[] uploadBytes = file.getBytes();
            String imageHash = ImageUtils.obtainHashOfByeArray(uploadBytes);
            // Get time to pull from Cache
            Date startDate = new Date();
            long nanoTimeStart = System.nanoTime();
            classifiedImage = classifyImageService.checkIfInCache(imageHash);
            long nanoTimeEnd = System.nanoTime();
            Date endDate = new Date();
            boolean cacheHit = false;
            if (classifiedImage != null) {
                cacheHit = true;
            } else {
                log.info("Batch {}. Classifying Image of Hash : {}", batchId, imageHash);
                // Get time to process Image
                startDate = new Date();
                nanoTimeStart = System.nanoTime();
                classifiedImage = classifyImageService.classifyImage(uploadBytes, imageHash);
                nanoTimeEnd = System.nanoTime();
                endDate = new Date();
            }
            CachePerformance cachePerformance = new CachePerformance(startDate,
                    endDate,
                    imageHash,
                    cacheHit,
                    fileName,
                    nanoTimeEnd - nanoTimeStart,
                    cacheTestingBatch,
                    fileSizekB);
            // Some service that will store this
            asynchDBService.saveEntity(cachePerformance);
        }
        return classifiedImage;
    }

    // For classifying the image with no performance measurement
    @PostMapping(value = "/classify")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@RequestBody MultipartFile file) throws IOException {
        boolean validImage = ImageUtils.verifyMultipartFileIsImage(file);
        ClassifiedImage classifiedImage = new ClassifiedImage("Unsupported Image Type", 100, null);
        if (validImage) {
            byte[] uploadBytes = file.getBytes();
            String imageHash = ImageUtils.obtainHashOfByeArray(uploadBytes);
            log.info("No Performance Measuring. Classifying Image of Hash : {}", imageHash);
            classifiedImage = classifyImageService.classifyImage(uploadBytes, imageHash);
            log.info("Classifying Finished for : {}", imageHash);
        }
        return classifiedImage;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
