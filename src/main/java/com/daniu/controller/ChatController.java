package com.daniu.controller;

import com.daniu.common.BaseResponse;
import com.daniu.common.ResultUtils;
import com.daniu.constant.AnythingllmConstant;
import com.daniu.model.anythingllm.RemoteChatRequest;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/workspace")
public class ChatController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AnythingllmConstant anythingllmConstant;

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        RemoteChatRequest remoteChatRequest = new RemoteChatRequest();
        BeanUtils.copyProperties(chatRequest, remoteChatRequest);

        String workspace = chatRequest.getWorkspace();

        HttpEntity<RemoteChatRequest> entity = new HttpEntity<>(remoteChatRequest);
        System.out.println(anythingllmConstant.workspaceUrl + "/" + workspace + "/chat");

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/" + workspace + "/chat",
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        return ResultUtils.success(response.getBody());
    }

}
