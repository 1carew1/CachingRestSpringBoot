package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.CacheTestingBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CacheTestingBatchRepository extends JpaRepository<CacheTestingBatch, Long> {
    @Query("SELECT c FROM CacheTestingBatch c WHERE c.id=?1")
    public CacheTestingBatch findOne (Long id);
}
