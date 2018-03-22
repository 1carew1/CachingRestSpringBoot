package com.colm.cachetest.cachingrest.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public class EntitySaver implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(EntitySaver.class);

    private JpaRepository repository;

    private Object entity;

    public EntitySaver(JpaRepository repository, Object entity) {
        this.repository = repository;
        this.entity = entity;
    }

    @Override
    public void run () {
        if (entity != null && repository != null) {
            repository.save(entity);
        }
        else {
            log.info("Entity is null so cannot saved");
        }
    }
}
