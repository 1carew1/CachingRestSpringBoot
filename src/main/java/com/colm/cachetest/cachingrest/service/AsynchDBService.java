package com.colm.cachetest.cachingrest.service;

import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.repository.CachePerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class AsynchDBService {

    @Autowired
    private CachePerformanceRepository cachePerformanceRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void savedCachePerformance (CachePerformance cachePerformance) {
        Runnable savingThread = () -> {
            Thread thisThread = Thread.currentThread();
            thisThread.setName("DBSave-" + thisThread.getName());
            cachePerformanceRepository.save(cachePerformance);
        };
        executor.execute(savingThread);
    }
}
