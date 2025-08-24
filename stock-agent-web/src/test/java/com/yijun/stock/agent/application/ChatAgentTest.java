package com.yijun.stock.agent.application;

import com.codersim.StockAgentMainApp;
import com.codersim.chat.service.ChatAppService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 16:29
 * @Description
 *
 */
@SpringBootTest(classes = {StockAgentMainApp.class})
@Slf4j
public class ChatAgentTest {
    @Resource
    private ChatAppService chatAppService;

    @Test
    public void testChat() {
        Flux<String> whoAreYou = chatAppService.chat("1", "", "who are you");
        System.out.println(whoAreYou.getPrefetch());
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
