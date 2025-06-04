package com.wll.controller;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.wll.pojo.ChatModel;
import com.wll.service.impl.AIServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

/**
 * @time 2025/3/12 22:46 周三
 */
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private AIServiceImpl aiService;


    @GetMapping(value = "/default")
    public Flux<String> getDefaultAIResponse(@RequestParam(value = "msg") String msg) {
        return aiService.getDefaultAIResponse(msg);
    }

    @GetMapping(value = "/deepseek")
    public Flux<String> generation1(@RequestParam(value = "msg") String msg) {
        return aiService.generation1(msg);
    }


    @GetMapping(value = "/qianwenplus", produces = MediaType.TEXT_HTML_VALUE)
    public String qianwenPlus(@RequestParam(value = "msg") String msg) throws NoApiKeyException, InputRequiredException {
        return aiService.qianwenPlus(msg);
    }

    @GetMapping(value = "/zhipu")
    public Flux<String> zhipuAi(String model, Double temperature, String message) {
        return aiService.zhipuAi(model, temperature, message);
    }

    @PostMapping("/zhipu")
    public Flux<String> zhipuAi(@RequestBody ChatModel chatModel, HttpServletRequest httpServletRequest) {
        return aiService.zhipuAi(chatModel, httpServletRequest);
    }
}
