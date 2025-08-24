package com.codersim.rag.service;

import com.codersim.aggregate.vector.service.MarkdownFileReaderDomainService;
import com.codersim.exception.StockAgentAppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:52
 * @Description
 * 使用 内存 Vector 实现的RAG 对话 生存RAG信息
 *
 */
@Service
@Slf4j
public class RagVectorAppService implements IRagAppService {

    /**
     * 使用 阿里的客户端
     */
    @Resource(name = "aLiChatClient")
    private ChatClient chatClient;


    @Resource
    private VectorStore simpleVectorStore;

    @Resource
    private MarkdownFileReaderDomainService markdownFileReaderDomainService;

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

    @Override
    public void initVectorData() {
        StopWatch watch = new StopWatch("initVectorData");
        List<Document> documents = null;
        watch.start("readMarkdownFile");
        try {
            documents = markdownFileReaderDomainService.readMarkdownFile();
        } catch (Exception e) {
            log.warn("markdown file read errror", e);
        }
        watch.stop();
        if (CollectionUtils.isEmpty(documents)) {
            log.warn("markdown file is empty");
            return;
        }
        watch.start("writeMarkdownFile to vectorstore");
        // 拆分 documents 列表为最大 25 个元素的子列表
        for (int i = 0; i < documents.size(); i += 25) {
            int end = Math.min(i + 25, documents.size());
            List<Document> subList = documents.subList(i, end);
            simpleVectorStore.add(subList);
        }
        watch.stop();
        log.info("writeMarkdownFile to vectorStore finished: cost: {}", watch.prettyPrint());
    }


}
