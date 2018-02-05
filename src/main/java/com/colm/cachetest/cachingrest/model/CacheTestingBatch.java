package com.colm.cachetest.cachingrest.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "batch")
public class CacheTestingBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    private Date startDate = new Date();
    String cacheType;

    public CacheTestingBatch () {
    }

    public CacheTestingBatch (String cacheType) {
        this.cacheType = cacheType;
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
}
