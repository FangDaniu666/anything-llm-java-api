package com.daniu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * anything llm api配置
 *
 * @author FangDaniu
 * @since 2024/06/03
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "anything-llm")
public class AnythingllmConfig {

    private String url;

    private String token;

}

