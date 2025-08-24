package com.codersim.controller;

import com.codersim.test.api.TestConsumer;
import jakarta.annotation.Resource;
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
public class TestController implements TestConsumer {

    @Resource(name = "aLiChatClient")
    private ChatClient chatClient;



    @Override
    @GetMapping("/ai")
    public String generation(@RequestParam("userInput") String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

}
