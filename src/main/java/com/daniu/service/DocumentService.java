package com.daniu.service;

import com.daniu.model.document.DocumentsResponse;
import com.daniu.model.workspace.WorkspaceGetResponse;

public interface DocumentService {

    DocumentsResponse.LocalFiles getAllDocuments();

    WorkspaceGetResponse addAllDocuments(String workspaceName);

    WorkspaceGetResponse removeAllDocuments(String workspaceName);

}
