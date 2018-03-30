package com.colm.cachetest.cachingrest.model.fe;

public class BatchInfo {

    private String cacheType;
    private String cacheSizeMb;
    private String evictionPolicy;

    public BatchInfo() {
    }

    public String getCacheType() {
        return cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public String getCacheSizeMb() {
        return cacheSizeMb;
    }

    public void setCacheSizeMb(String cacheSizeMb) {
        this.cacheSizeMb = cacheSizeMb;
    }

    public String getEvictionPolicy() {
        return evictionPolicy;
    }

    public void setEvictionPolicy(String evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }
}
