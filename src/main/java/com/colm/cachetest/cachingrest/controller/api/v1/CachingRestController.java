package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.db.CacheTestingBatch;
import com.colm.cachetest.cachingrest.model.fe.BatchInfo;
import com.colm.cachetest.cachingrest.model.fe.ClassifiedImage;
import com.colm.cachetest.cachingrest.service.CacheTestingBatchService;
import com.colm.cachetest.cachingrest.service.ClassificationCacheManipulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class CachingRestController {

    @Autowired
    private ClassificationCacheManipulationService classificationService;
    @Autowired
    private CacheTestingBatchService batchService;

    // create a batch
    @PostMapping(value = "/batch")
    @CrossOrigin(origins = "*")
    public CacheTestingBatch createBatch(@RequestBody BatchInfo batchInfo) {
        return batchService.createBatch(batchInfo);
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
