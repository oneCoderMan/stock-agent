package com.codersim.chat.api;

import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 15:50
 * @Description
 *
 */
public interface ChatConsumer {
    Flux<String> chat(String userInput, String chatId);
}
