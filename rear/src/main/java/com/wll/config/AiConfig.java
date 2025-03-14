package com.wll.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.ai.openai.OpenAiChatOptions;
//import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * @time 2025/3/13 6:11 周四
 */
@Configuration
public class AiConfig {

    // 直接标记自动配置的 openAiChatModel 为首选 Bean
//    @Primary
//    @Bean
//    public ChatModel deepSeekAiChatModel(@Qualifier("openAiChatModel") ChatModel openAiChatModel) {
//        return openAiChatModel;
//    }
    @Primary  // 标记为首选Bean
    @Bean
    public ChatModel zhipuAiChatModel(@Qualifier("zhiPuAiChatModel") ChatModel zhiPuAiChatModel) {
        return zhiPuAiChatModel;
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory memory) {
        return chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor()
                        , new MessageChatMemoryAdvisor(memory)
                        , new PromptChatMemoryAdvisor(memory)
                )
                .build();
    }

//    @Bean
//    public ChatClient chatClient(ChatClient.Builder chatClientBuilder/*, ChatMemory chatMemory*//*,VectorStore vectorStore*/
//    ) {
//        return chatClientBuilder
//                .defaultAdvisors(
//                        new MessageChatMemoryAdvisor(chatMemory), // CHAT MEMORY
//                        new QuestionAnswerAdvisor(vectorStore), // RAG
//                        new SimpleLoggerAdvisor() // using for debugging and monitoring your AI interactions.
//                )
//                .build();
//    }


    @Bean
    public InMemoryChatMemory inMemoryChatMemory() {
        return new InMemoryChatMemory();
    }
}
