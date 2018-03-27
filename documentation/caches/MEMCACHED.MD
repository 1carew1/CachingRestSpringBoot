# Memcached Setup

## Summary and Setup
memcached can be used as an external cache. Note that the max cached item size is 1MB and the eviction policy is LRU.

It can be setup via :

```
docker run --name memcached -p 11211:11211 -d memcached -m 128
```

- `-m` is the size of the cache in MB. Must be greater than 64 MB
- Can set the max item size with `-I 10m` to set it to 10 MB
## application.properties
All other entries will need to be commented out from the appropriate application.properties file (dev, prod, test).
The entry should look like :

```
# Memcached - External
memcached.cache.servers=localhost:11211
memcached.cache.mode=static
memcached.cache.expiration=86400
```

## build.gradle
All other cache entries will need to be commented out and the following to be uncommented :

```
compile 'io.sixhours:memcached-spring-boot-starter:1.2.0'
```