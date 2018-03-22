package com.colm.cachetest.cachingrest.service;

import com.colm.cachetest.cachingrest.model.CachePerformance;
import com.colm.cachetest.cachingrest.model.CacheRemainder;
import com.colm.cachetest.cachingrest.repository.CachePerformanceRepository;
import com.colm.cachetest.cachingrest.repository.CacheRemainderRepository;
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
    @Autowired
    private CacheRemainderRepository cacheRemainderRepository;

    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("DBSaver-%d").build();
    private Executor executor = Executors.newSingleThreadExecutor(namedThreadFactory);

    public void saveEntity(Object entity) {
        Runnable savingThread = null;
        if(entity instanceof CachePerformance) {
            savingThread = new CachePerformanceSaver(cachePerformanceRepository, entity);
        } else if(entity instanceof CacheRemainder) {
            savingThread = new CachePerformanceSaver(cacheRemainderRepository, entity);
        }
        if(savingThread != null) {
            executor.execute(savingThread);
        }
    }
}
