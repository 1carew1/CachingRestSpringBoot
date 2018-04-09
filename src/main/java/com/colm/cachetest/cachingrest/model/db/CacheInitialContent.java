package com.colm.cachetest.cachingrest.model.db;


import javax.persistence.*;

// For the initial content in cache after filling but before testing
@Entity
@Table(name = "cache_initial_content")
public class CacheInitialContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    private String imageHash;
    private Long fileSizekB;
    private String fileName;

    @ManyToOne
    @JoinColumn (name="batch_id")
    private CacheTestingBatch cacheTestingBatch;

    public CacheInitialContent(){}

    public CacheInitialContent(String imageHash, Long fileSizekB, String fileName, CacheTestingBatch cacheTestingBatch) {
        this.imageHash = imageHash;
        this.fileSizekB = fileSizekB;
        this.fileName = fileName;
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

    public Long getFileSizekB() {
        return fileSizekB;
    }

    public void setFileSizekB(Long fileSizekB) {
        this.fileSizekB = fileSizekB;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
