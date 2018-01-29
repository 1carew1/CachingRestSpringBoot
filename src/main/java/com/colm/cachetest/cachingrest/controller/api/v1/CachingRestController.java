package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.model.CacheTestingBatch;
import com.colm.cachetest.cachingrest.model.ClassifiedImage;
import com.colm.cachetest.cachingrest.service.AsynchDBService;
import com.colm.cachetest.cachingrest.service.CacheTestingBatchService;
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
@RequestMapping("/api/v1")
public class CachingRestController {

    private static final Logger log = LoggerFactory.getLogger(CachingRestController.class);
    @Autowired
    private ClassifyImageService classifyImageService;
    @Autowired
    private AsynchDBService asynchDBService;
    @Autowired
    private CacheTestingBatchService cacheTestingBatchService;


    @PostMapping(value = "/batch")
    @CrossOrigin(origins = "*")
    public CacheTestingBatch createBatch() {
        return cacheTestingBatchService.createBatch();
    }

    @PostMapping(value = "/classify/{batchId}")
    @CrossOrigin(origins = "*")
    public ClassifiedImage classifyImage(@PathVariable Long batchId, @RequestBody MultipartFile file) throws IOException {
        boolean validImage = ImageUtils.verifyMultipartFileIsImage(file);
        CacheTestingBatch cacheTestingBatch = cacheTestingBatchService.obtainBatch(batchId);
        ClassifiedImage classifiedImage = new ClassifiedImage("Unsupported Image Type", 100, null);
        if (validImage && cacheTestingBatch != null) {
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
                classifyImageService.evictFromCache(imageHash);
                log.info("Classifying Image of Hash : {}", imageHash);
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
                    cacheTestingBatch);
            // Some service that will store this
            asynchDBService.savedCachePerformance(cachePerformance);
        }
        return classifiedImage;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
