package com.codersim.controller;

import com.codersim.mcp.api.McpConsumer;
import com.codersim.mcp.service.McpAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/31 16:02
 * @Description
 *
 */
@RestController
@Tag(name = "RAG APIs")
@RequestMapping("/stock/agent/")
@Slf4j
public class McpController implements McpConsumer {
    @Resource
    private McpAppService mcpAppService;

    private String userInput1 = "北京的天气如何？";


    @Override
    @PostMapping("/mcpTest")
    @Operation(summary = "mcp")
    public Flux<String> mcpTest(@RequestBody String userInput) {
        return mcpAppService.chat(userInput);
    }
}
