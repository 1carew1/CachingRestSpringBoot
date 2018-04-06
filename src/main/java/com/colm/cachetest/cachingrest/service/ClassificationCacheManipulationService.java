package com.colm.cachetest.cachingrest.service;


import com.colm.cachetest.cachingrest.model.db.CachePerformance;
import com.colm.cachetest.cachingrest.model.db.CacheRemainder;
import com.colm.cachetest.cachingrest.model.db.CacheTestingBatch;
import com.colm.cachetest.cachingrest.model.fe.ClassifiedImage;
import com.colm.cachetest.cachingrest.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.MissingResourceException;

@Service
public class ClassificationCacheManipulationService {

    private static final Logger log = LoggerFactory.getLogger(ClassificationCacheManipulationService.class);

    @Autowired
    private AsynchDBService asynchDBService;
    @Autowired
    private CacheTestingBatchService batchService;
    @Autowired
    private ClassifyImageService classifyImageService;

    public ClassifiedImage checkCache(Long batchId, MultipartFile file) {
        ClassifiedImage classifiedImage = null;
        CacheTestingBatch batch = batchService.obtainBatch(batchId);
        if (batch == null) {
            throw new MissingResourceException("Batch is not available", null, null);
        }
        String imageHash = ImageUtils.obtainFileHashFromMultipartFile(file);
        log.info("Batch {}. Trying to Pull Image from Cache with hash : {}", batchId, imageHash);
        classifiedImage = classifyImageService.checkIfInCache(imageHash);
        if (classifiedImage != null) {
            Long fileSizekB = file.getSize() / 1024;
            String fileName = file.getOriginalFilename();

            CacheRemainder cacheRemainder = new CacheRemainder(imageHash, fileSizekB, fileName, batch);
            asynchDBService.saveEntity(cacheRemainder);
        }
        return classifiedImage;
    }

    public ClassifiedImage classifyImageWithCachePerformanceMeasurements(Long batchId, MultipartFile file) {
        CacheTestingBatch cacheTestingBatch = batchService.obtainBatch(batchId);
        ClassifiedImage classifiedImage = null;
        if (cacheTestingBatch == null) {
            throw new MissingResourceException("Batch is not available", null, null);
        }
        Long fileSizekB = file.getSize() / 1024;
        String fileName = file.getOriginalFilename();
        byte[] fileBytes = ImageUtils.obtainBytesFromMultipartFile(file);
        String imageHash = ImageUtils.obtainHashOfByeArray(fileBytes);
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
            classifiedImage = classifyImageService.classifyImage(fileBytes, imageHash);
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
        asynchDBService.saveEntity(cachePerformance);
        return classifiedImage;
    }

    public ClassifiedImage classifyImage(MultipartFile file) {
        String imageHash = ImageUtils.obtainFileHashFromMultipartFile(file);
        byte[] fileBytes = ImageUtils.obtainBytesFromMultipartFile(file);
        log.info("Classifying Image without metrics. Hash : {}", imageHash);
        return classifyImageService.classifyImage(fileBytes, imageHash);
    }

}
