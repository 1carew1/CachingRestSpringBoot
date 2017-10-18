package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.CubedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CubedInfoRepository extends JpaRepository<CubedInfo, Long> {
    @Query ("SELECT ci FROM CubedInfo ci WHERE ci.number=?1")
    public CubedInfo findOne(Long number);
}
