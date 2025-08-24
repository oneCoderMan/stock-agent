package com.codersim.model;

import com.codersim.exception.StockAgentAppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 21:48
 * @Description
 *
 */
@Service
@Slf4j
public class ModelService {

    @Resource
    private Map<String, ChatModel> chatModelMap;

    public ChatModel getChatModel(String name) {
        if (MapUtils.isEmpty(chatModelMap)) {
            throw new StockAgentAppException("miss chat model config");
        }
        ChatModel chatModel = chatModelMap.get(name);

        if (chatModel == null) {
            log.error("chat model not found, name={}", name);
            return null;
        }
        return chatModel;
    }

}
