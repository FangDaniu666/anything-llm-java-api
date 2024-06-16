package com.daniu.controller;

import com.daniu.constant.AnythingllmConstant;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.RemoteChatRequest;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * @param chatRequest 聊天请求
     * @return {@link SseEmitter }
     */
    @GetMapping("/workspace/stream-chat")
    public SseEmitter streamChat(@RequestBody ChatRequest chatRequest) {
        SseEmitter sseEmitter = new SseEmitter();

        RemoteChatRequest remoteChatRequest = new RemoteChatRequest();
        BeanUtils.copyProperties(chatRequest, remoteChatRequest);
        String workspace = chatRequest.getWorkspace();

        webClient.post()
                .uri(anythingllmConstant.workspaceUrl + "/" + workspace + "/stream-chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(remoteChatRequest)
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
