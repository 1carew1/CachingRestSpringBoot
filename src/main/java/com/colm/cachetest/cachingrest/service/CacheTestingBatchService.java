package com.colm.cachetest.cachingrest.service;


import com.colm.cachetest.cachingrest.model.CacheTestingBatch;
import com.colm.cachetest.cachingrest.repository.CacheTestingBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CacheTestingBatchService {

    @Autowired
    private CacheTestingBatchRepository cacheTestingBatchRepository;

    public CacheTestingBatch obtainBatch(Long id) {
        CacheTestingBatch cacheTestingBatch = cacheTestingBatchRepository.findOne(id);
        if(cacheTestingBatch != null && cacheTestingBatch.getEndDate() != null) {
            cacheTestingBatch = null;
        }
        return cacheTestingBatch;
    }

    public CacheTestingBatch createBatch(String cacheType, String setupComment) {
        String sC = "Dry Run";
        if(setupComment != null && !setupComment.isEmpty()) {
            sC = setupComment;
        }
        CacheTestingBatch cacheTestingBatch = new CacheTestingBatch(cacheType, sC);
        cacheTestingBatchRepository.save(cacheTestingBatch);
        return cacheTestingBatch;
    }

    public CacheTestingBatch completeBatch(Long batchId){
        CacheTestingBatch cacheTestingBatch = obtainBatch(batchId);
        if(cacheTestingBatch != null){
            cacheTestingBatch.setEndDate(new Date());
            cacheTestingBatchRepository.save(cacheTestingBatch);
        }
        return cacheTestingBatch;
    }
}
