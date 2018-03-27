# ehcache 2 Setup

## Summary
Disable the cache through configuration.

Can also be done by removing the `@EnableCaching` annotation in `CachingrestApplication.java`.

## application.properties
All other cache entries will need to be commented out from the appropriate application.properties file (dev, prod, test) depending on your desired environment.
Then no cache entry should look like :

```
spring.cache.type=none
```

## build.gradle
All other cache entries will need to be commented out