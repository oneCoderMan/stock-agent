package com.codersim.mcp.service;

import com.codersim.exception.StockAgentAppException;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/31 16:08
 * @Description
 *
 */
@Service
public class McpAppService {
    @Resource(name = "aLiChatMcpClient")
    private ChatClient chatClient;




    public Flux<String> chat(String prompt) {
        if (StringUtils.isEmpty(prompt)) {
            throw new StockAgentAppException("prompt is empty");
        }
        return chatClient.prompt().user(prompt).stream().content();
    }

}
