package com.codersim.chat.service;

import com.alibaba.cloud.ai.dashscope.api.DashScopeResponseFormat;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 16:01
 * @Description
 *
 */
@Service
@Slf4j
public class ChatAppService {

    @Resource(name = "aLiChatClient")
    private ChatClient chatClient;


    public Flux<String> chat(String chatId, String model, String prompt) {
        log.debug("chat model is: {}", model);

        var runtimeOptions = DashScopeChatOptions.builder()
                .withModel(model)
                .withTemperature(0.8)
                .withResponseFormat(DashScopeResponseFormat.builder()
                        .type(DashScopeResponseFormat.Type.TEXT)
                        .build())
                .build();

        // 带有记忆功能的客户端
        ChatClient.ChatClientRequestSpec clientRequestSpec = chatClient.prompt()
                .options(runtimeOptions)
                .advisors(memoryAdvisor -> memoryAdvisor
                        .param(ChatMemory.CONVERSATION_ID, chatId))
                .user(prompt);


        return clientRequestSpec.stream().content();
    }
}
