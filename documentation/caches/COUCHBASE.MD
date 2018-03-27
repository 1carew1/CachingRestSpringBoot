# Couchbase Setup

## Summary and Setup
couchbase can be used as an external cache. Note that it overflows to disk and does not have control over the eviction policy (LFU).

It can be setup via :

```
docker run -d --name db -p 8091-8094:8091-8094 -p 11210:11210 couchbase   
```

- Once the docker image is running visit : `http://localhost:8091`
- Setup a new cluster. Name it whatever you want and used default settings.
- Once setup create a new bucket named : `cachetest`. Minimum allowed size is 100MB.
- Go to security and add a new user `couchbaseuser` with password `password`. Give full bucket permission of the bucket that was just created.

## application.properties
All cache entries will need to be commented out from the appropriate application.properties file (dev, prod, test).

## build.gradle
All other cache entries will need to be commented out and the following to be uncommented :

```
compile group: 'com.couchbase.client', name: 'java-client'
compile group: 'com.couchbase.client', name: 'couchbase-spring-cache'
```


## CachingrestApplication.java
The following needs to be added to the end of the mentioned file above :

```
    @Bean(destroyMethod = "disconnect")
    public Cluster cluster() {
        String serverIp = "localhost";
        return CouchbaseCluster.create(serverIp);
    }

    @Bean(destroyMethod = "close")
    public Bucket bucket() {
        cluster().authenticate("couchbaseuser", "password");
        return cluster().openBucket("cachetest");
    }

    @Bean
    public CacheManager cacheManager() {
        CacheBuilder cacheBuilder = CacheBuilder.newInstance(bucket()).withExpiration(0);
        return new CouchbaseCacheManager(cacheBuilder, "imageClassifications");
    }
```