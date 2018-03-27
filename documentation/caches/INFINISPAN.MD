# Infinispan Setup

## Summary
infinispan is an internal cache so it is run within the JVM so no external setup is required here.
To use infinispan in the project the application-dev.properties or application-prod.properties file needs to be edited alongside the build.gradle file for the correct import/s.

Once these are setup, the cache can be configured through `src/main/java/resources/infinispan/infinispan.xml`.

## application.properties
All other cache entries will need to be commented out from the appropriate application.properties file (dev, prod, test) whatever is the desired environment.
Then entry should look like :

```
# Infispan - Internal
spring.cache.cache-names=imageClassifications
spring.cache.type=infinispan
spring.cache.infinispan.config=classpath:infinispan/infinispan.xml
```

## build.gradle
All other cache entries will need to be commented out and the following to be uncommented :

```
compile group: 'org.infinispan', name: 'infinispan-spring', version: '7.2.5.Final'
compile group: 'org.infinispan', name: 'infinispan-embedded', version: '7.2.5.Final'
```


## infinispan.xml
The location of this file is `src/main/java/resources/infinispan/infinispan.xml`.

Setting up the cache looks like :

```
    <cache-container default-cache="default">
        <local-cache name="imageClassifications">
            <eviction strategy="LIRS" max-entries="10" />
        </local-cache>
    </cache-container>
```
- `max-entries` is the number of entries allowed in the cache. Note this is number of entries not the size in MB.
- `strategy` is the eviction policy used. It can be : `NONE`, `LRU` or `LRIS`
