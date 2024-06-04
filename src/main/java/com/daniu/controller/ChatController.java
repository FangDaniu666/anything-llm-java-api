package com.daniu.controller;

import com.daniu.common.BaseResponse;
import com.daniu.common.ResultUtils;
import com.daniu.constant.AnythingllmConstant;
import com.daniu.model.anythingllm.RemoteChatRequest;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import com.daniu.model.workspace.WorkspaceResponse;
import com.daniu.model.workspace.WorkspaceResponseWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @GetMapping(value = "/list")
    public BaseResponse<List<WorkspaceResponse>> listWorkspaces() {

        ResponseEntity<WorkspaceResponseWrapper> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "s",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        WorkspaceResponseWrapper workspaceResponses = response.getBody();

        return ResultUtils.success(workspaceResponses.getWorkspaces());
    }

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
