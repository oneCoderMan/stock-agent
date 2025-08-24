package com.codersim.rag.service;

import com.codersim.exception.StockAgentAppException;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:52
 * @Description
 * 使用 内存 Vector 实现的RAG
 *
 */
@Service
public class RagVectorAppService implements IRagAppService {

    /**
     * 使用 阿里的客户端
     */
    @Resource(name = "aLiChatClient")
    private ChatClient chatClient;


    @Resource
    private VectorStore simpleVectorStore;

    /**
     *
     * @param chatId
     * @param prompt
     * @return
     */
    @Override
    public Flux<String> ragChat(String chatId, String prompt) {
        if (StringUtils.isEmpty(chatId)) {
            throw new StockAgentAppException("chat id is empty");
        }
        if (StringUtils.isEmpty(prompt)) {
            throw new StockAgentAppException("prompt is empty");
        }

        return chatClient.prompt()
                .user(prompt)
                .advisors(memoryAdvisor -> memoryAdvisor
                        .param(ChatMemory.CONVERSATION_ID, chatId)
                ).advisors(
                        QuestionAnswerAdvisor
                                .builder(simpleVectorStore)
                                .searchRequest(
                                        SearchRequest.builder()
                                                .topK(6)
                                                .build()
                                )
                                .build()
                ).stream()
                .content();
    }
}
