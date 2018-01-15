package com.colm.cachetest.cachingrest.service;

import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.repository.CachePerformanceRepository;
import com.colm.cachetest.cachingrest.threads.CachePerformanceSaver;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Service
public class AsynchDBService {

    @Autowired
    private CachePerformanceRepository cachePerformanceRepository;

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("DBSaver-%d").build();
    private Executor executor = Executors.newSingleThreadExecutor(namedThreadFactory);

    public void savedCachePerformance (CachePerformance cachePerformance) {
        Runnable savingThread = new CachePerformanceSaver(cachePerformanceRepository, cachePerformance);
        executor.execute(savingThread);
    }
}
