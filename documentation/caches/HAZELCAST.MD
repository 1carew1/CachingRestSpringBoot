# hazelcast Setup

## Summary
hazelcast is an internal cache so it is run within the JVM so no external setup is required here.
To use hazelcast in the project the application-dev.properties or application-prod.properties file needs to be edited alongside the build.gradle file for the correct import/s.

Once these are setup, the cache can be configured through `src/main/java/resources/hazelcast/hazelcast.xml`.

## application.properties
All other cache entries will need to be commented out from the appropriate application.properties file (dev, prod, test) whatever is the desired environment.
Then entry should look like :

```
# Hazelcast - Internal
spring.cache.cache-names=imageClassifications
spring.cache.type=hazelcast
spring.hazelcast.config=classpath:hazelcast/hazelcast.xml
```

## build.gradle
All other cache entries will need to be commented out and the following to be uncommented :

```
compile group: 'com.hazelcast', name: 'hazelcast'
compile group: 'com.hazelcast', name: 'hazelcast-spring'
```


## hazelcast.xml
The location of this file is `src/main/java/resources/hazelcast/hazelcast.xml`.

Setting up the cache looks like :

```
    <map name="imageClassifications">
        <max-size policy="USED_HEAP_SIZE">4</max-size>
        <eviction-policy>LRU</eviction-policy>
    </map>
```
- `<max-size policy="USED_HEAP_SIZE">NUMBER</max-size>` is the size of the cache in MB.
- `<eviction-policy>POLICY</eviction-policy>` is the eviction policy used. It can be : `NONE`, `LRU` or `LFU`
