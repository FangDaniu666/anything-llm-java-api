package com.daniu.controller;

import com.daniu.constant.AnythingllmConstant;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 流式聊天控制器
 *
 * @author FangDaniu
 * @since 2024/06/07
 */
@RestController
public class StreamChatController {

    @Resource
    private AnythingllmConstant anythingllmConstant;

    @Resource
    private WebClient webClient;

    /**
     * 流式聊天
     *
     * @param message 消息
     * @return {@link SseEmitter }
     */
    @GetMapping("/workspace/stream-chat")
    public SseEmitter streamChat(@RequestParam String message) {
        SseEmitter sseEmitter = new SseEmitter();
        String payload = String.format("{\"message\": \"%s\", \"mode\": \"chat\"}", message);

        webClient.post()
                .uri(anythingllmConstant.workspaceUrl + "/ollama-demo/stream-chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(response -> {
                    try {
                        sseEmitter.send(SseEmitter.event().data(response));
                    } catch (Exception e) {
                        sseEmitter.completeWithError(e);
                    }
                })
                .doOnComplete(sseEmitter::complete)
                .doOnError(sseEmitter::completeWithError)
                .subscribe();

        return sseEmitter;
    }

}
