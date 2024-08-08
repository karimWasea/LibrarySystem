package Librar.Library.Config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheingManager {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("books", "patrons"); // Cache names
    }
}
