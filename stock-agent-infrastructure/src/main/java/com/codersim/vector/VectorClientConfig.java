package com.codersim.vector;

import com.codersim.aggregate.vector.event.VectorStoreDelegate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 18:02
 * @Description
 *
 */
@Configuration
public class VectorClientConfig {


    /**
     * 提供基于内存的向量存储（SimpleVectorStore）
     * 依赖 EmbeddingModel（自动注入，Alibaba 的 embedding 模型）
     * @param embeddingModel
     * @return
     */
    @Bean
    public VectorStore simpleVectorStore(@Qualifier("dashscopeEmbeddingModel") EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    public VectorStoreDelegate vectorStoreDelegate(
            @Qualifier("simpleVectorStore") VectorStore simpleVectorStore,
            @Qualifier("analyticdbVectorStore") @Autowired(required = false) VectorStore analyticdbVectorStore) {
        return new VectorStoreDelegate(simpleVectorStore, analyticdbVectorStore);
    }
}
