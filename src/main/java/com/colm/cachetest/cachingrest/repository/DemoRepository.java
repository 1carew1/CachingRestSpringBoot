package com.colm.cachetest.cachingrest.repository;

import com.colm.cachetest.cachingrest.model.DemoObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemoRepository extends JpaRepository<DemoObject, Long> {
    public List<DemoObject> findAll();
    public DemoObject findByName(String name);
}
