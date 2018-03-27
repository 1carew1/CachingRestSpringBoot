# ehcache 2 Setup

## Summary
ehcache is an internal cache so it is run within the JVM so no external setup is required here.
To use ehcache in the project the application-dev.properties or application-prod.properties file needs to be edited alongside the build.gradle file for the correct import/s.

Once cache these are setup, the cache can be configured through `src/main/java/resources/ehcach2/ehcache2.xml`.

## application.properties
All other cache entries will need to be commented out from the appropriate application.properties file (dev, prod, test).
Then ehcache entry should look like :

```
# EhCache 2 - Internal
spring.cache.cache-names=imageClassifications
spring.cache.type=ehcache
spring.cache.ehcache.config=classpath:ehcache2/ehcache2.xml
```

## build.gradle
All other cache entries will need to be commented out and the following to be uncommented :

```compile group: 'net.sf.ehcache', name: 'ehcache', version: '2.10.4'```


## ehcache.xml
The location of this file is `src/main/java/resources/ehcach2/ehcache2.xml`.
Note the file is named `echcache2.xml`.

Setting up the cache looks like :

```
    <cache name="imageClassifications"
           maxBytesLocalHeap="1M"
           eternal="true"
           overflowToDisk="false"
           memoryStoreEvictionPolicy="LFU">
    </cache>
```
`maxBytesLocalHeap` is the size of the cache.

`eternal` means the cache entries do not timeout/expire. The items only get evicted when full based on the replacement policy.

`overflowToDisk` determines where to store on system storage or memory only. False means memory only.

`memoryStoreEvictionPolicy` is the cache replacement policy.
