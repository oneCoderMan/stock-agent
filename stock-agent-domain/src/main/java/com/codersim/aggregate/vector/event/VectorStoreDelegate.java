package com.codersim.aggregate.vector.event;

import org.springframework.ai.vectorstore.VectorStore;

import java.util.Objects;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 18:09
 * @Description use when multi vector
 *
 */
public class VectorStoreDelegate {
    private VectorStore simpleVectorStore;

    private VectorStore analyticdbVectorStore;

    public VectorStoreDelegate(VectorStore simpleVectorStore, VectorStore analyticdbVectorStore) {
        this.simpleVectorStore = simpleVectorStore;
        this.analyticdbVectorStore = analyticdbVectorStore;
    }

    public VectorStore getVectorStore(String vectorStoreType) {

        if (Objects.equals(vectorStoreType, "analyticdb") && analyticdbVectorStore != null) {
            return analyticdbVectorStore;
        }

        return simpleVectorStore;
    }
}
