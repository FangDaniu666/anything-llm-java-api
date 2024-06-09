package com.daniu.service.impl;

import com.daniu.constant.AnythingllmConstant;
import com.daniu.model.workspace.WorkspaceGetResponse;
import com.daniu.service.WorkspaceService;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
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
}



