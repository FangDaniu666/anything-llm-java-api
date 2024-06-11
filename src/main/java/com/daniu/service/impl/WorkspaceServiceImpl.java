package com.daniu.service.impl;

import com.daniu.constant.AnythingllmConstant;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import com.daniu.model.chat.RemoteChatRequest;
import com.daniu.model.workspace.WorkspaceGetResponse;
import com.daniu.model.workspace.WorkspaceNewResponse;
import com.daniu.model.workspace.WorkspaceResponseWrapper;
import com.daniu.service.WorkspaceService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AnythingllmConstant anythingllmConstant;

    @Override
    public WorkspaceResponseWrapper getAllWorkspace() {
        ResponseEntity<WorkspaceResponseWrapper> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "s",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Override
    public WorkspaceNewResponse getNewWorkspace(String name) {
        HttpEntity<String> entity = new HttpEntity<>(name);

        ResponseEntity<WorkspaceNewResponse> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/new",
                HttpMethod.POST,
                entity,
                WorkspaceNewResponse.class
        );

        return response.getBody();
    }

    @Override
    public WorkspaceGetResponse getWorkspaceByName(String workspaceName) {

        ResponseEntity<WorkspaceGetResponse> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/" + workspaceName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    @Override
    public ResponseEntity<String> deleteWorkspace(String workspaceName) {
        return restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/" + workspaceName,
                HttpMethod.DELETE,
                null,
                String.class);
    }

    @Override
    public ResponseEntity<ChatResponse> getChat(ChatRequest chatRequest) {
        RemoteChatRequest remoteChatRequest = new RemoteChatRequest();
        BeanUtils.copyProperties(chatRequest, remoteChatRequest);
        String workspace = chatRequest.getWorkspace();

        HttpEntity<RemoteChatRequest> entity = new HttpEntity<>(remoteChatRequest);

        return restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/" + workspace + "/chat",
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );
    }
}




