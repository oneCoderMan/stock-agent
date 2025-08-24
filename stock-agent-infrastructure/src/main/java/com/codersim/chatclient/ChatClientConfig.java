package com.codersim.chatclient;

import com.codersim.model.ModelService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 16:11
 * @Description
 *
 */
@Configuration
public class ChatClientConfig {
    @Resource(name = "defaultPromptTemplate")
    private PromptTemplate defaultPromptTemplate;

    @Resource
    private ModelService modelService;

    @Resource
    private MessageChatMemoryAdvisor messageChatMemoryAdvisor;

    private static final String DASH_SCOPE_CHAT_MODEL = "dashscopeChatModel";

    @Bean("aLiChatClient")
    public ChatClient initDashChatClient() {
        ChatModel chatModel = modelService.getChatModel(DASH_SCOPE_CHAT_MODEL);
        return ChatClient.builder(chatModel)
                .defaultSystem(defaultPromptTemplate.getTemplate())
                .defaultAdvisors(messageChatMemoryAdvisor)
                .build();
    }
}
