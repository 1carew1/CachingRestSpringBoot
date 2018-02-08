package com.colm.cachetest.cachingrest.service;


import com.colm.cachetest.cachingrest.model.CacheTestingBatch;
import com.colm.cachetest.cachingrest.repository.CacheTestingBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheTestingBatchService {

    @Autowired
    private CacheTestingBatchRepository cacheTestingBatchRepository;

    public CacheTestingBatch obtainBatch(Long id) {
        return cacheTestingBatchRepository.findOne(id);
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
}
