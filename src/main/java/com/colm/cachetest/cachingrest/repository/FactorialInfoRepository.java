package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.FactorialInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FactorialInfoRepository extends JpaRepository<FactorialInfo, Long> {
    @Query ("SELECT f FROM FactorialInfo f WHERE f.number=?1")
    public FactorialInfo findOne(Long number);
}
