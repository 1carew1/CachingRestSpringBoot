package com.colm.cachetest.cachingrest.service;

import com.colm.cachetest.cachingrest.model.CubedInfo;
import com.colm.cachetest.cachingrest.repository.CubedInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CubedCacheService {

    @Autowired
    private CubedInfoRepository cubedInfoRepository;

    @CachePut (value = "cubedInfos", key = "#someNumber")
    public CubedInfo storeAndCacheCube (long someNumber, BigDecimal cubedOfSomeNumber) {
        CubedInfo cubedInfo = new CubedInfo(someNumber, cubedOfSomeNumber);
        cubedInfoRepository.save(cubedInfo);
        return cubedInfo;
    }

    @Cacheable (value = "cubedInfos", key = "#someNumber")
    public CubedInfo getCubedInfo (long someNumber) {
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
        CubedInfo cubedInfo = cubedInfoRepository.findOne(someNumber);
        if (cubedInfo == null) {
            cubedInfo = storeAndCacheCube(someNumber, bigDecimal);
        }
        return cubedInfo;
    }

    @CacheEvict (value = "cubedInfos", key = "#someNumber")
    public void evict (long someNumber) {
    }

}
