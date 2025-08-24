package com.codersim.rag.service;

import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:53
 * @Description
 * 使用ES 实现的RAG
 */
public class RagEsAppService implements IRagAppService {
    @Override
    public Flux<String> ragChat(String chatId, String prompt) {
        return null;
    }
}
