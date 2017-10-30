package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.service.CubedCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CubeDatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CubeDatabaseLoader.class);

    @Autowired
    private CubedCacheService cubedCacheService;

    private final CubedInfoRepository repository;

    @Autowired
    public CubeDatabaseLoader (CubedInfoRepository demoRepository) {
        this.repository = demoRepository;
    }

    @Override
    public void run (String... string) throws Exception {
        for (int i = 1; i <= 300; i++) {
            long number = i;
            BigDecimal bigDecimal = BigDecimal.valueOf(number * number * number);
            log.info("Trying to put cube into cache : " + number + ", cube = " + bigDecimal);
            cubedCacheService.storeAndCacheCube(number, bigDecimal);
        }
    }
}
