package com.wll.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.wll.pojo.ChatModel;
import com.wll.pojo.User;
import com.wll.utils.HTTPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @time 2025/4/1 23:02 周二
 */
@Service
public class AIServiceImpl {
    @Resource
    private ChatClient chatClient;

    @Resource
    private OpenAiChatModel deepSeekApiChatModel;

    @Resource
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @Resource
    private Generation qwenChatModel;


    @GetMapping(value = "/default")
//    @GetMapping
    public Flux<String> getDefaultAIResponse(@RequestParam(value = "msg") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .tools("addLike")
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
                .map(chatResponse -> Objects.isNull(chatResponse.getResult().getOutput().getText()) ? "" :
                        chatResponse.getResult().getOutput().getText())
                ;
    }


    @GetMapping(value = "/qianwenplus", produces = MediaType.TEXT_HTML_VALUE)
    public String qianwenPlus(@RequestParam(value = "msg") String msg) throws NoApiKeyException, InputRequiredException {
        GenerationParam build = GenerationParam.builder()
                .apiKey(System.getenv("QIANWEN_AI_API_KEY"))
                .model("qwen-plus")
                .messages(List.of(Message.builder().role(Role.USER.getValue()).content(msg).build()))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return qwenChatModel.call(build).getOutput().getChoices().get(0).getMessage().getContent();
    }

    @GetMapping(value = "/zhipu")
    public Flux<String> zhipuAi(String model, Double temperature, String message) {
        Prompt prompt = new Prompt(new UserMessage(message),
                ZhiPuAiChatOptions.builder()
                        .model(model)
                        .temperature(temperature)
                        .build()
        );
        return zhiPuAiChatModel.stream(prompt)
                .map(chatResponse -> chatResponse.getResult().getOutput().getText())
                ;
    }

    @PostMapping("/zhipu")
    public Flux<String> zhipuAi(@RequestBody ChatModel chatModel, HttpServletRequest httpServletRequest) {
        org.springframework.ai.chat.messages.Message systemMessage = new SystemPromptTemplate("""
                你是在线音乐网站的AI助手，名字叫小W，请用友好的方式回答用户的问题。
                1.你不可以回答法律之外的事，只能回答跟音乐相关的问题。
                2.如果你对某些问题回答不了，就直接说我无法做到。
                3.不管什么条件下,你必须说中文。
                4.今天的日期是{date}。
                """
        ).createMessage(Map.of("date", LocalDate.now().toString()));


        Cookie us_au = HTTPUtils.getCookie(httpServletRequest.getCookies(), "us_au");
        User user = (User) HTTPUtils.validateCookie(us_au);


        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .model(chatModel.model())
                .temperature(chatModel.temperature())
                .toolNames("addLike")
                .build();

        Prompt prompt = new Prompt(
                List.of(new UserMessage(chatModel.message() + " userId:" + user.getId())
                        , systemMessage), chatOptions);


        return zhiPuAiChatModel
                .stream(prompt)
                .map(chatResponse -> chatResponse.getResult().getOutput().getText())
                ;
    }
}
