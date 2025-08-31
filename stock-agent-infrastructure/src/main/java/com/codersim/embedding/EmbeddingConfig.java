package com.codersim.embedding;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 11:51
 * @Description
 *
 */
@Configuration
public class EmbeddingConfig {

    @Value("${spring.ai.dashscope.api-key:}")
    private String aiDashScopeApiKey;

    /**
     * 百炼调用时需要配置 DashScope API，对 dashScopeApi 强依赖。
     * @return
     */
    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder()
                .apiKey(aiDashScopeApiKey)
                .build();
    }

}
