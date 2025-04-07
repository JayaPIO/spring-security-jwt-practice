package com.example.spring_security_jwt.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching // enables caching mechanism
public class CacheConfig {
    // no explicit bean required while using default ConcurrentMapCacheManager

    @Bean
    public CaffeineCacheManager cacheManager(){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("usersCache");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // cache expires after 10 minutes
                .maximumSize(100) // Max 100 items in cache
        );
        return cacheManager;
    }
}
