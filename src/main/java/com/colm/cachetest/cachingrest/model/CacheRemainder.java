package com.colm.cachetest.cachingrest.model;


import javax.persistence.*;

@Entity
@Table(name = "cache_remainder")
public class CacheRemainder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    private String imageHash;
    @ManyToOne
    @JoinColumn (name="batch_id")
    private CacheTestingBatch cacheTestingBatch;

    public CacheRemainder(){}

    public CacheRemainder(String imageHash, CacheTestingBatch cacheTestingBatch) {
        this.imageHash = imageHash;
        this.cacheTestingBatch = cacheTestingBatch;
    }

    public Long getId() {
        return id;
    }

    public String getImageHash() {
        return imageHash;
    }

    public void setImageHash(String imageHash) {
        this.imageHash = imageHash;
    }

    public CacheTestingBatch getCacheTestingBatch() {
        return cacheTestingBatch;
    }

    public void setCacheTestingBatch(CacheTestingBatch cacheTestingBatch) {
        this.cacheTestingBatch = cacheTestingBatch;
    }
}
