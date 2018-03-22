package com.colm.cachetest.cachingrest.threads;

import com.colm.cachetest.cachingrest.model.CachePerformance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;

public class CachePerformanceSaver implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CachePerformanceSaver.class);

    private JpaRepository repository;

    private Object entity;

    public CachePerformanceSaver (JpaRepository repository, Object entity) {
        this.repository = repository;
        this.entity = entity;
    }

    @Override
    public void run () {
        if (entity != null && repository != null) {
            repository.save(entity);
        }
        else {
            log.info("Cache Performance object is null so cannot save");
        }
    }
}
