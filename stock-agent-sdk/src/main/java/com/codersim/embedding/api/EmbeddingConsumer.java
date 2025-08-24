package com.codersim.embedding.api;

import org.springframework.ai.embedding.EmbeddingResponse;

import java.util.Map;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 11:27
 * @Description
 *
 */
public interface EmbeddingConsumer {
    public Map<String, EmbeddingResponse> embed(String message);
}
