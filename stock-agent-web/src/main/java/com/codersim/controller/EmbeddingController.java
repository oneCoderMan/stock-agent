package com.codersim.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.codersim.embedding.api.EmbeddingConsumer;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 11:28
 * @Description
 *
 */
@RestController
@Tag(name = "Embedding APIs")
@RequestMapping("/stock/agent/")
@Slf4j
public class EmbeddingController implements EmbeddingConsumer {
    @Resource(name = "dashscopeEmbeddingModel")
    private DashScopeEmbeddingModel dashScopeEmbeddingModel;

    @GetMapping("/ai/embedding")
    public Map<String, EmbeddingResponse> embed(@RequestParam("message") String message) {
        EmbeddingResponse embeddingResponse = dashScopeEmbeddingModel
                .embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }
}
