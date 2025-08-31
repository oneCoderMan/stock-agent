package com.codersim.mcp.api;

import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/31 16:01
 * @Description
 *
 */
public interface McpConsumer {
    Flux<String> mcpTest(@RequestBody String userInput);
}
