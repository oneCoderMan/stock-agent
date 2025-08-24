package com.codersim.rag.api;

import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:47
 * @Description
 *
 */
public interface RagConsumer {
    Flux<String> ragChat(String prompt, String chatId);
}
