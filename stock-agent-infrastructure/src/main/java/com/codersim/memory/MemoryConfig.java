package com.codersim.memory;

import com.alibaba.cloud.ai.memory.jdbc.SQLiteChatMemoryRepository;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author： yijun
 * @DATE: 2025/8/24 09:34
 * @Description
 *
 */
@Configuration
public class MemoryConfig {
    /**
     * 基于SQL lite的记忆模块
     * @param jdbcTemplate
     * @return
     */
    @Bean
    public ChatMemory SQLiteChatMemory(JdbcTemplate jdbcTemplate) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(SQLiteChatMemoryRepository.sqliteBuilder()
                        .jdbcTemplate(jdbcTemplate)
                        .build())
                .build();
    }

    /**
     * memoryAdvisor
     * @param sqLiteChatMemory
     * @return
     */
    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(ChatMemory sqLiteChatMemory) {
        return MessageChatMemoryAdvisor.builder(sqLiteChatMemory).build();
    }
}
