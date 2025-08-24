package com.codersim.common;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 22:41
 * @Description
 *
 */
@Configuration
public class SystemConfig {
    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor() {

        return new SimpleLoggerAdvisor(100);
    }

}
