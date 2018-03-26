package com.colm.cachetest.cachingrest.model;

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
    private String setupComment;

    public CacheTestingBatch () {
    }

    public CacheTestingBatch (String cacheType, String setupComment) {
        this.cacheType = cacheType;
        this.setupComment = setupComment;
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

    public String getSetupComment() {
        return setupComment;
    }

    public void setSetupComment(String setupComment) {
        this.setupComment = setupComment;
    }
}
