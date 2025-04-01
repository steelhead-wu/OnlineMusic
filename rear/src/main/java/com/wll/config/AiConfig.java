package com.wll.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
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
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @time 2025/3/13 6:11 周四
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

//    @Primary  // 标记为首选Bean
//    @Bean
//    public ChatModel zhipuAiChatModel() {
//        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi(System.getenv("ZHIPU_AI_API_KEY"));
//        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi("2a2b96976ff44651b64b0daba109ef27.BQAw3ytpnb6HCFXr");
//        return new ZhiPuAiChatModel(zhiPuAiApi, ZhiPuAiChatOptions.builder()
//                .model(ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue())
//                .temperature(0.4)
//                .maxTokens(200)
//                .build());
//        return new ZhiPuAiChatModel(zhiPuAiApi,
//                ZhiPuAiChatOptions.builder()
//                        .model("GLM-3-Turbo")
//                        .build());

//        return new ZhiPuAiChatModel(new ZhiPuAiApi("2a2b96976ff44651b64b0daba109ef27.BQAw3ytpnb6HCFXr"),
//                ZhiPuAiChatOptions.builder()
//                        .model(ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue())
//                        .temperature(0.4)
//                        .maxTokens(200)
//                        .build()
//        );
//    }

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


    @Bean
    public InMemoryChatMemory inMemoryChatMemory() {
        return new InMemoryChatMemory();
    }
}
