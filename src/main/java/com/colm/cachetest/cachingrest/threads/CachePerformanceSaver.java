package com.colm.cachetest.cachingrest.threads;

import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.repository.CachePerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachePerformanceSaver implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CachePerformanceSaver.class);

    private CachePerformanceRepository cachePerformanceRepository;

    private CachePerformance cachePerformance;

    public CachePerformanceSaver (CachePerformanceRepository cachePerformanceRepository, CachePerformance cachePerformance) {
        this.cachePerformanceRepository = cachePerformanceRepository;
        this.cachePerformance = cachePerformance;
    }

    @Override
    public void run () {
        if (cachePerformance != null && cachePerformanceRepository != null) {
            log.info("Saving Cache Performance to DB");
            cachePerformanceRepository.save(cachePerformance);
        }
        else {
            log.info("Cache Performance object is null");
        }
    }
}
