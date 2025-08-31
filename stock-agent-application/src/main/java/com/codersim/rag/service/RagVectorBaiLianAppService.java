package com.codersim.rag.service;

import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeCloudStore;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentCloudReader;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentCloudReaderOptions;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeStoreOptions;
import com.codersim.aggregate.vector.service.MarkdownFileReaderDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @Author： yijun
 * @DATE: 2025/8/25 23:02
 * @Description
 * 使用 baiLian Vector 实现的RAG 对话 生存RAG信息
 */
@Service
@Slf4j
public class RagVectorBaiLianAppService implements IRagAppService {
    /**
     * 使用 阿里的客户端
     */
    @Resource(name = "aLiChatClient")
    private ChatClient chatClient;

    /**
     * baiLian 平台的API
     */
    @Resource
    private DashScopeApi dashScopeApi;

    /**
     * 知识库名字
     */
    @Value("${spring.ai.alibaba.stock.bailian.index-name:test-rag}")
    private String indexName;

    @Resource
    private MarkdownFileReaderDomainService markdownFileReaderDomainService;

    @Override
    public Flux<String> ragChat(String chatId, String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .advisors(memoryAdvisor -> memoryAdvisor
                        .param(ChatMemory.CONVERSATION_ID, chatId)
                ).advisors(
                        new DocumentRetrievalAdvisor(
                                new DashScopeDocumentRetriever(
                                        dashScopeApi,
                                        DashScopeDocumentRetrieverOptions.builder()
                                                .withIndexName(indexName)
                                                .build()
                                )
                        )
                ).stream()
                .content();
    }

    @Override
    public void initVectorData(String fileDirPath) {
        if (StringUtils.isEmpty(fileDirPath)) {
            return;
        }

        log.info("init rag vector data, {}", fileDirPath);
        // 1. import and split documents
        DocumentReader reader = new DashScopeDocumentCloudReader(fileDirPath,
                dashScopeApi, new DashScopeDocumentCloudReaderOptions("cate_5b21e38f913b4d7885d920c60ce5f112_12593537"));

        List<Document> documentList = reader.get();
        log.info("{} documents loaded and split", documentList.size());

        // 1. add documents to DashScope cloud storage
        VectorStore vectorStore = new DashScopeCloudStore(dashScopeApi,
                new DashScopeStoreOptions(indexName));
        vectorStore.add(documentList);
        log.info("{} documents added to dashscope cloud vector store", documentList.size());
    }
}
