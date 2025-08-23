package com.codersim.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： yijun
 * @DATE: 2025/8/22 22:45
 * @Description
 *
 */
@RestController
public class TestController {
//    @Resource
    private final ChatClient chatClient;

    public TestController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @GetMapping("/ai")
    String generation(@RequestParam("userInput") String userInput) {
//        return userInput;
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

}
