package com.colm.cachetest.cachingrest.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "cache_performance")
public class CachePerformance{

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    private Date startDate;
    private Date finishDate;
    private String imageHash;
    private Boolean cacheHit;
    private String fileName;
    private Long elapsedTimeInns;
    @ManyToOne
    @JoinColumn (name="batch_id")
    private CacheTestingBatch cacheTestingBatch;

    public CachePerformance () {
    }

    public CachePerformance (Date startDate, Date finishDate, String imageHash, Boolean cacheHit, String fileName, Long elapsedTimeInns, CacheTestingBatch cacheTestingBatch) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.imageHash = imageHash;
        this.cacheHit = cacheHit;
        this.fileName = fileName;
        this.elapsedTimeInns = elapsedTimeInns;
        this.cacheTestingBatch = cacheTestingBatch;
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

    public Long getId () {
        return id;
    }

    public String getFileName () {
        return fileName;
    }

    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public Long getElapsedTimeInns() {
        return elapsedTimeInns;
    }

    public void setElapsedTimeInns(Long elapsedTimeInns) {
        this.elapsedTimeInns = elapsedTimeInns;
    }

    public CacheTestingBatch getCacheTestingBatch() {
        return cacheTestingBatch;
    }
}
