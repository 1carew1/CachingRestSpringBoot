package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.DemoObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemoRepository extends JpaRepository<DemoObject, Long> {
    public DemoObject findByName(String name);
    @Query ("SELECT h FROM DemoObject h WHERE h.id=?1")
    public DemoObject findOne(Long id);
}
