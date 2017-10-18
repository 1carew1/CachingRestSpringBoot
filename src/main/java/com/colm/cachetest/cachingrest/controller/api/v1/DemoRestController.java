package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.DemoObject;
import com.colm.cachetest.cachingrest.repository.DemoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping ("/api/v1")
public class DemoRestController {

    private static final Logger log = LoggerFactory.getLogger(DemoRepository.class);


    @Autowired
    private DemoRepository demoRepository;

    @RequestMapping (value = "/demo", method = RequestMethod.GET)
    public List<DemoObject> listAllDemos () {
        log.info("Finding All Demo Objects");

        return demoRepository.findAll();
    }

    @RequestMapping (value = "/cube/{someNumber}", method = RequestMethod.GET)
    public BigDecimal doSomeComplexMaths (@PathVariable int someNumber) {
        log.info("Cubing : " + someNumber);
        Long times = 0l;
        //This essential Cubes a number
        for (int i = 0; i < someNumber; i++) {
            for (int j = 0; j < someNumber; j++) {
                for (int k = 0; k < someNumber; k++) {
                    times++;
                }
            }
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(times);
        log.info(someNumber + "^3 = " + bigDecimal.toString());
        return bigDecimal;
    }

    @Cacheable (value = "post-single", key = "#id", unless = "#result.shares < 10")
    @RequestMapping (value = "/demo/{id}", method = RequestMethod.GET)
    public DemoObject findDemoById (@PathVariable Long id) {
        return demoRepository.findOne(id);
    }
}
