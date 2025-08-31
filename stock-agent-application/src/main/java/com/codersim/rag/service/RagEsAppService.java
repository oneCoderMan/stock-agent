package com.codersim.rag.service;

import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 17:53
 * @Description
 * 使用ES 实现的RAG
 */
public class RagEsAppService implements IRagAppService {
    /**
     * todo 完成 ES的存储
     * @param chatId
     * @param prompt
     * @return
     */
    @Override
    public Flux<String> ragChat(String chatId, String prompt) {
        return null;
    }

    /**
     * TODO 完成 ES 的写入
     */
    @Override
    public void initVectorData(String fileDirPath) {

    }
}
