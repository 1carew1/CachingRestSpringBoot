package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.CubedInfo;
import com.colm.cachetest.cachingrest.model.FactorialInfo;
import com.colm.cachetest.cachingrest.service.CubedCacheService;
import com.colm.cachetest.cachingrest.service.FactorialCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1")
public class CachingRestController {

    private static final Logger log = LoggerFactory.getLogger(CachingRestController.class);

    @Autowired
    private CubedCacheService cubedCacheService;
    @Autowired
    private FactorialCacheService factorialCacheService;

    @RequestMapping (value = "/cube/{someNumber}", method = RequestMethod.GET)
    public CubedInfo doSomeComplexMaths (@PathVariable Long someNumber) {
        log.info("Cubing : " + someNumber);
        CubedInfo cubedInfo = cubedCacheService.getCubedInfo(someNumber);
        log.info(someNumber + "^3 = " + cubedInfo.getNumberCubed().toString());
        return cubedInfo;
    }

    @RequestMapping (value = "/factorial/{someNumber}", method = RequestMethod.GET)
    public FactorialInfo computerTheFactorial (@PathVariable int someNumber) {
        log.info("Getting Factorial of : " + someNumber);
        FactorialInfo factorialInfo = factorialCacheService.computerFactorial(someNumber);
        log.info(someNumber + "! = " + factorialInfo.getFactorial().toString());
        return factorialInfo;
    }
}
