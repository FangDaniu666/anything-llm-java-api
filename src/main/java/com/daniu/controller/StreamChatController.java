package com.daniu.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@RestController
public class StreamChatController {

    private final WebClient webClient;

    public StreamChatController() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(60));
        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:3001")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer DBCFN9E-RBX44G9-P9N5SHN-V6VSGQC")
                .build();
    }

    @GetMapping("/stream-chat")
    public SseEmitter streamChat(@RequestParam String message) {
        SseEmitter sseEmitter = new SseEmitter();
        String payload = String.format("{\"message\": \"%s\", \"mode\": \"chat\"}", message);

        webClient.post()
                .uri("/api/v1/workspace/ollama-demo/stream-chat")
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
