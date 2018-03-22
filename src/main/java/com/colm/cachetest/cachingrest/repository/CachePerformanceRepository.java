package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.CachePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CachePerformanceRepository extends JpaRepository<CachePerformance, Long> {
}

