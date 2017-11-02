package com.colm.cachetest.cachingrest.repository;


import com.colm.cachetest.cachingrest.service.FactorialCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FactorialDatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FactorialDatabaseLoader.class);

    @Autowired
    private FactorialCacheService factorialCacheService;

    private final FactorialInfoRepository repository;

    @Autowired
    public FactorialDatabaseLoader (FactorialInfoRepository demoRepository) {
        this.repository = demoRepository;
    }

    @Override
    public void run (String... string) throws Exception {

    }
}
