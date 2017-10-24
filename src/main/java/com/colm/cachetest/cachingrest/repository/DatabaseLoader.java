package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.service.CubedCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseLoader implements CommandLineRunner {


    @Autowired
    private CubedCacheService cubedCacheService;

    private final CubedInfoRepository repository;

    @Autowired
    public DatabaseLoader (CubedInfoRepository demoRepository) {
        this.repository = demoRepository;
    }

    @Override
    public void run (String... string) throws Exception {
        for (int i = 1; i <= 1200; i++) {
            long number =  i;
            BigDecimal bigDecimal = BigDecimal.valueOf(number * number * number);
            cubedCacheService.storeAndCacheCube(number, bigDecimal);
        }
    }
}
