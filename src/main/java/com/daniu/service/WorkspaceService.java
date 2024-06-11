package com.daniu.service;

import com.daniu.model.workspace.WorkspaceGetResponse;
import com.daniu.model.workspace.WorkspaceNewResponse;
import com.daniu.model.workspace.WorkspaceResponseWrapper;

public interface WorkspaceService {

    WorkspaceResponseWrapper getAllWorkspace();

    WorkspaceNewResponse getNewWorkspace(String name);

    WorkspaceGetResponse getWorkspaceByName(String workspaceName);

}
