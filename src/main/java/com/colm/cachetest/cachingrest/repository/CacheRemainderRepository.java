package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.db.CacheRemainder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRemainderRepository extends JpaRepository<CacheRemainder, Long> {
}
