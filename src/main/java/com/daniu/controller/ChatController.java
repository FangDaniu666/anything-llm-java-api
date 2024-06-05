package com.daniu.controller;

import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.constant.AnythingllmConstant;
import com.daniu.exception.ThrowUtils;
import com.daniu.model.anythingllm.RemoteChatRequest;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import com.daniu.model.workspace.NewWorkspaceResponse;
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
        ThrowUtils.throwIf(workspaceResponses == null, ErrorCode.OPERATION_ERROR, "获取工作空间列表失败");

        return ResultUtils.success(workspaceResponses.getWorkspaces());
    }

    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<WorkspaceResponse> createWorkspace(@RequestBody String name) {
        ThrowUtils.throwIf(name == null || name.isEmpty(), ErrorCode.PARAMS_ERROR, "请求参数错误");

        HttpEntity<String> entity = new HttpEntity<>(name);

        ResponseEntity<NewWorkspaceResponse> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/new",
                HttpMethod.POST,
                entity,
                NewWorkspaceResponse.class
        );

        NewWorkspaceResponse newWorkspaceResponse = response.getBody();

        ThrowUtils.throwIf(newWorkspaceResponse == null, ErrorCode.OPERATION_ERROR, "新建工作空间失败");

        return ResultUtils.success(newWorkspaceResponse.getWorkspace());
    }

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        ThrowUtils.throwIf(chatRequest == null, ErrorCode.PARAMS_ERROR, "请求参数错误");

        RemoteChatRequest remoteChatRequest = new RemoteChatRequest();
        BeanUtils.copyProperties(chatRequest, remoteChatRequest);
        String workspace = chatRequest.getWorkspace();

        HttpEntity<RemoteChatRequest> entity = new HttpEntity<>(remoteChatRequest);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/" + workspace + "/chat",
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        return ResultUtils.success(response.getBody());
    }

}
