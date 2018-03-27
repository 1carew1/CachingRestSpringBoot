# Redis Setup

## Summary and Setup
redis can be used as an external cache.
The cache is configured through a redis.conf file

It can be setup via :

```
docker run -p 6379:6379 -v /myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf --name myredis -d redis:4.0.2 redis-server /usr/local/etc/redis/redis.conf
```

Ensure to set `/myredis/conf/redis.conf` to the fully qualified location of the redis.conf you want to use.

## redis.conf

```
maxmemory 128mb
maxmemory-policy allkeys-lru
```

- `maxmemory` is the maximum size of the cache
- `maxmemory-policy` can be : `noeviction`, `allkeys-lru`, `allkeys-random`

Note there needs to be over 10 elements in cache for eviction to take place else an exception wll be thrown.

## application.properties
All other entries will need to be commented out from the appropriate application.properties file (dev, prod, test).
The entry should look like :

```
# Redis - External
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

## build.gradle
All other cache entries will need to be commented out and the following to be uncommented :

```
compile "org.springframework.boot:spring-boot-starter-data-redis"
compile "redis.clients:jedis:2.6.1"
```