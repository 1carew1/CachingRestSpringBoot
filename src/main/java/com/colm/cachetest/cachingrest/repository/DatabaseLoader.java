package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.DemoObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final DemoRepository demoRepository;

    @Autowired
    public DatabaseLoader (DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    @Override
    public void run (String... string) throws Exception {
        for (int i = 1; i <= 100; i++) {
            demoRepository.save(new DemoObject("Username" + i));
        }
    }
}
