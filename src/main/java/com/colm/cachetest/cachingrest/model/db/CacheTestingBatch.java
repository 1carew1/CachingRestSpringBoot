package com.colm.cachetest.cachingrest.model.db;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "batch")
public class CacheTestingBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    private Date startDate = new Date();
    private String cacheType;
    private String cacheSizeMb;
    private String evictionPolicy;
    private Date endDate;

    public CacheTestingBatch () {
    }

    public CacheTestingBatch(String cacheType, String cacheSizeMb, String evictionPolicy) {
        this.startDate = new Date();
        this.cacheType = cacheType;
        this.cacheSizeMb = cacheSizeMb;
        this.evictionPolicy = evictionPolicy;
    }

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getCacheType () {
        return cacheType;
    }

    public void setCacheType (String cacheType) {
        this.cacheType = cacheType;
    }

    public String getCacheSizeMb() {
        return cacheSizeMb;
    }

    public void setCacheSizeMb(String cacheSizeMb) {
        this.cacheSizeMb = cacheSizeMb;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEvictionPolicy() {
        return evictionPolicy;
    }

    public void setEvictionPolicy(String evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }
}
