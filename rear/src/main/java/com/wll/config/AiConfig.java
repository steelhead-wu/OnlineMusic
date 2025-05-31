package com.wll.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.datastax.oss.driver.internal.core.context.DefaultDriverContext;
import com.datastax.oss.driver.internal.core.session.DefaultSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.cassandra.CassandraChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.cassandra.CassandraChatMemoryRepositoryConfig;
//import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
//import org.springframework.ai.chat.memory.repository.jdbc.MysqlChatMemoryRepositoryDialect;
import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.ai.openai.OpenAiChatOptions;
//import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @datetime 2025/3/13 6:11 周四
 */
@Configuration
public class AiConfig {

    // 直接标记自动配置的 openAiChatModel 为首选 Bean
//    @Primary
    @Bean
    public ChatModel deepSeekAiChatModel(@Qualifier("openAiChatModel") ChatModel openAiChatModel) {
        return openAiChatModel;
    }

    @Primary  // 标记为首选Bean
    @Bean
    public ChatModel zhipuAiChatModel(@Qualifier("zhiPuAiChatModel") ChatModel zhiPuAiChatModel) {
        return zhiPuAiChatModel;
    }

    @Bean
    public Generation generation() {
        return new Generation();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory memory) {
        return chatClientBuilder
//                .defaultSystem(i -> i.text("你是在线音乐网站的AI助手，请用友好的方式回答用户的问题，不管什么条件下,你必须说中文。今天的日期是{date}")
//                        .params(Map.of("date", LocalDate.now().toString()))
                .defaultSystem(i -> i.text("你是在线音乐网站的AI助手，请用友好的方式回答用户的问题，不管什么条件下,你必须说中文。")

                )
                .defaultAdvisors(new SimpleLoggerAdvisor()
//                        , new MessageChatMemoryAdvisor(memory) // 短期记忆
//                        , new PromptChatMemoryAdvisor(memory) // 长期记忆
                )

                .build();
    }


//    @Bean
//    public ChatMemoryRepository getChatMemoryRepository(JdbcTemplate jdbcTemplate) {
//        return JdbcChatMemoryRepository.builder()
//                .jdbcTemplate(jdbcTemplate)
//                .dialect(new MysqlChatMemoryRepositoryDialect())
//                .build();
//    }


//    @Bean
//    @Primary
//    public ChatMemoryRepository chatMemoryRepository() {
//        return CassandraChatMemoryRepository
//                .create(CassandraChatMemoryRepositoryConfig.builder()
//                        .withCqlSession(DefaultSession.init())
//                        .build());
//    }

//    @Bean
//    public ChatMemory chatMemory() {
//        return MessageWindowChatMemory.builder()
//                 设置最大的聊天记录条数，也就是说只能保存50条聊天记录
//                .chatMemoryRepository(chatMemoryRepository)
//                .maxMessages(50)
//                .build();
//    }

//    @Bean
//    public InMemoryChatMemory inMemoryChatMemory() {
//        return new InMemoryChatMemory();
//    }
}
