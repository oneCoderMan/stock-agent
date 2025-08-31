package com.codersim.controller;

import com.codersim.rag.api.RagConsumer;
import com.codersim.rag.service.IRagAppService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

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
    private Map<String, IRagAppService> ragAppServiceMap;

    @Resource
    private ThreadPoolTaskExecutor fileThreadPoolExecutor;

    private static final String RAG_APP_NAME = "ragVectorBaiLianAppService";

    private IRagAppService getRagAppService() {
        return ragAppServiceMap.get(RAG_APP_NAME);
    }

    @Override
    @PostMapping("/rag")
    @Operation(summary = "RAG")
    public Flux<String> ragChat(@RequestBody String prompt,
                                @RequestHeader(value = "chatId", required = false, defaultValue = "chat-agent") String chatId) {
        log.info("receive rag chat request, prompt = {}", prompt);
        return getRagAppService().ragChat(chatId, prompt);
    }


    /**
     * 初始化内存的向量数据库，用于RAG召回
     * @return
     */
    @GetMapping("/initRagVector")
    @Operation(summary = "RAG")
    public String initRagVector(@RequestParam("filePath") String filePath) {
        fileThreadPoolExecutor.submit(() -> {
            try {
                getRagAppService().initVectorData(filePath);
            } catch (RuntimeException e) {
                 log.error("执行initVectorData异常", e);
                throw e; // 重新抛出异常，让线程池知道任务失败了
            }
        });
        return "success";
    }

}
