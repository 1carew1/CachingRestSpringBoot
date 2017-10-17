package com.colm.cachetest.cachingrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class CachingrestApplication {

	@Primary
	@Bean (name = "cacheManager")
	public CacheManager cacheManager() {
		return new NoOpCacheManager();
	}

	public static void main(String[] args) {
		SpringApplication.run(CachingrestApplication.class, args);
	}
}
