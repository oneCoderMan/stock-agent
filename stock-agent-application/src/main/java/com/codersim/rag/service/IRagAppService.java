package com.codersim.rag.service;

import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:51
 * @Description
 *
 */
public interface IRagAppService {
    Flux<String> ragChat(String chatId, String prompt);

    void initVectorData();
}
