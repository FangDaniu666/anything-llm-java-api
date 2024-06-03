package com.daniu.controller;

import com.daniu.common.BaseResponse;
import com.daniu.common.ResultUtils;
import com.daniu.config.AnythingllmConfig;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天接口
 *
 * @author FangDaniu
 * @since 2024/05/13
 */

@RestController
@RequestMapping("/api/v1/workspace")
public class ChatController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AnythingllmConfig anythingllmApiConfig;

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + anythingllmApiConfig.getToken());

        HttpEntity<ChatRequest> entity = new HttpEntity<>(chatRequest, headers);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                anythingllmApiConfig.getUrl(),
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        return ResultUtils.success(response.getBody());
    }

}
