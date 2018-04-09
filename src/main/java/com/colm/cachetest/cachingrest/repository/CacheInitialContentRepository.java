package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.db.CacheInitialContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheInitialContentRepository extends JpaRepository<CacheInitialContent, Long> {
}
