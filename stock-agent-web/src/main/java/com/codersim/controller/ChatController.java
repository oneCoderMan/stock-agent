package com.codersim.controller;

import com.codersim.chat.api.ChatConsumer;
import com.codersim.chat.service.ChatAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 15:55
 * @Description
 *
 */
@RestController
@Tag(name = "Chat APIs")
@RequestMapping("/stock/agent/")
@Slf4j
public class ChatController implements ChatConsumer {
    @Resource
    private ChatAppService chatAppService;

    private static final String DEFAULT_MODEL = "qwen-plus";

    @Override
    @PostMapping("/chat")
    @Operation(summary = "Flux Chat")
    public Flux<String> chat(@RequestBody String userInput,
                             @RequestHeader(value = "chatId", required = false, defaultValue = "chat-agent") String chatId) {
        log.info("receive input: {}", userInput);
        return chatAppService.chat("test", DEFAULT_MODEL, userInput);
    }
}
