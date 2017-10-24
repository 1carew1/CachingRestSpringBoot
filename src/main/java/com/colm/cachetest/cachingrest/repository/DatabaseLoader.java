package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.controller.api.v1.CubeInfoRestController;
import com.colm.cachetest.cachingrest.service.CubedCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    @Autowired
    private CubedCacheService cubedCacheService;

    private final CubedInfoRepository repository;

    @Autowired
    public DatabaseLoader (CubedInfoRepository demoRepository) {
        this.repository = demoRepository;
    }

    @Override
    public void run (String... string) throws Exception {
        for (int i = 1; i <= 27000; i++) {
            long number = i;
            BigDecimal bigDecimal = BigDecimal.valueOf(number * number * number);
//            log.info("Trying to put cube into cache : " + number + ", cube = " + bigDecimal);
            cubedCacheService.storeAndCacheCube(number, bigDecimal);
        }
    }
}
