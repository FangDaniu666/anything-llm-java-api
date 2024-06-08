package com.daniu.service;

import com.daniu.model.workspace.WorkspaceGetResponse;

public interface WorkspaceService {

    WorkspaceGetResponse getWorkspaceByName(String workspaceName);

}
