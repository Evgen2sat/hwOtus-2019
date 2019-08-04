package ru.otus.hw13;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw13.cache.CacheEngine;
import ru.otus.hw13.cache.CacheEngineImpl;
import ru.otus.hw13.dto.User;

@Configuration
public class Config {
    @Bean
    public CacheEngine<Long, User> initUserCache() {
        return new CacheEngineImpl<>(3, 10000, false);
    }
}
