package com.daniu.service;

import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import com.daniu.model.workspace.WorkspaceGetResponse;
import com.daniu.model.workspace.WorkspaceNewResponse;
import com.daniu.model.workspace.WorkspaceResponseWrapper;
import org.springframework.http.ResponseEntity;

public interface WorkspaceService {

    WorkspaceResponseWrapper getAllWorkspace();

    WorkspaceNewResponse getNewWorkspace(String name);

    WorkspaceGetResponse getWorkspaceByName(String workspaceName);

    ResponseEntity<String> deleteWorkspace(String workspaceName);

    ResponseEntity<ChatResponse> getChat(ChatRequest chatRequest);

}
