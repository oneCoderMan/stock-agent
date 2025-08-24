package com.codersim.controller;

import com.codersim.rag.api.RagConsumer;
import com.codersim.rag.service.RagVectorAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:48
 * @Description
 *
 */
@RestController
@Tag(name = "RAG APIs")
@RequestMapping("/stock/agent/")
@Slf4j
public class RagController implements RagConsumer {
    @Resource
    private RagVectorAppService ragVectorAppService;

    @Resource
    private ThreadPoolTaskExecutor fileThreadPoolExecutor;

    @Override
    @PostMapping("/rag")
    @Operation(summary = "RAG")
    public Flux<String> ragChat(@RequestBody String prompt,
                                @RequestHeader(value = "chatId", required = false, defaultValue = "chat-agent") String chatId) {
        log.info("receive rag chat request, prompt = {}", prompt);
        return ragVectorAppService.ragChat(chatId, prompt);
    }

    /**
     * 初始化内存的向量数据库，用于RAG召回
     * @return
     */
    @GetMapping("/initRagVector")
    @Operation(summary = "RAG")
    public String initRagVector() {
        fileThreadPoolExecutor.submit(() -> ragVectorAppService.initVectorData());
        return "success";
    }

}
