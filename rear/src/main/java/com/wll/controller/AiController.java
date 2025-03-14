package com.wll.controller;

import com.wll.utils.R;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

/**
 * @time 2025/3/12 22:46 周三
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private ChatClient chatClient;

    @Resource
    private OpenAiChatModel deepSeekApiChatModel;

    @Resource
    private ZhiPuAiChatModel zhiPuAiChatModel;


    @GetMapping(value = "/default", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getDefaultAIResponse(@RequestParam(value = "msg") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .stream()
                .content()
                .concatWithValues("[complete]")
                ;
    }

    @GetMapping(value = "/deepseek")
    public Flux<String> generation1(@RequestParam(value = "msg") String msg) {
        Flux<ChatResponse> responseFlux = deepSeekApiChatModel.stream(new Prompt(msg, OpenAiChatOptions
                .builder()
                .model("deepseek-reasoner")
                .temperature(1.3)
                .build()
        ));
        return responseFlux
                .map(chatResponse -> chatResponse.getResult().getOutput().getText())
                .onErrorResume(e -> Flux.just("Error: " + e.getMessage()));
    }


    @GetMapping(value = "/chat2")
    public Flux<ChatResponse> generation2(@RequestParam(value = "msg") String msg) {

        return zhiPuAiChatModel.stream(new Prompt(msg, ZhiPuAiChatOptions.builder().model(ZhiPuAiApi.ChatModel.GLM_4.value).temperature(0.7).build()));
    }

}
