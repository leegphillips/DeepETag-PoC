package com.github.leegphillips.DeepETagPoC;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    public SKURepository skuRepository() {
        return Mockito.mock(SKURepository.class);
    }
}
