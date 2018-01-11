package com.colm.cachetest.cachingrest.model;

import java.io.Serializable;
import java.util.Date;

public class CachePerformance implements Serializable {

    private Date startDate;
    private Date finishDate;
    private String imageHash;
    private Boolean cacheHit;

    public CachePerformance () {

    }

    public CachePerformance (Date startDate, Date finishDate, String imageHash, Boolean cacheHit) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.imageHash = imageHash;
        this.cacheHit = cacheHit;
    }

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate () {
        return finishDate;
    }

    public void setFinishDate (Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getImageHash () {
        return imageHash;
    }

    public void setImageHash (String imageHash) {
        this.imageHash = imageHash;
    }

    public Boolean getCacheHit () {
        return cacheHit;
    }

    public void setCacheHit (Boolean cacheHit) {
        this.cacheHit = cacheHit;
    }
}
