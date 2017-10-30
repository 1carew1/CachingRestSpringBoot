package com.colm.cachetest.cachingrest.service;


import ch.obermuhlner.math.big.BigDecimalMath;
import com.colm.cachetest.cachingrest.model.FactorialInfo;
import com.colm.cachetest.cachingrest.repository.FactorialInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FactorialCacheService {

    private static final Logger log = LoggerFactory.getLogger(FactorialCacheService.class);

    @Autowired
    private FactorialInfoRepository factorialInfoRepository;

    @Cacheable (value = "factorials", key = "#someNumber")
    public FactorialInfo computerFactorial (int someNumber) {
        FactorialInfo factorialInfo = factorialInfoRepository.findOne((long) someNumber);
        if (factorialInfo == null) {
            BigDecimal factorial = BigDecimalMath.factorial(someNumber);
            factorialInfo = new FactorialInfo((long) someNumber, factorial.toString());
            factorialInfoRepository.save(factorialInfo);
        }
        return factorialInfo;
    }
}
