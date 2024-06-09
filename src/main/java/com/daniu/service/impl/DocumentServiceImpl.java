package com.daniu.service.impl;

import com.daniu.common.ErrorCode;
import com.daniu.constant.AnythingllmConstant;
import com.daniu.exception.BusinessException;
import com.daniu.exception.ThrowUtils;
import com.daniu.model.document.DocumentResponseWrapper;
import com.daniu.model.document.DocumentsResponse;
import com.daniu.model.workspace.WorkspaceGetResponse;
import com.daniu.model.workspace.WorkspaceUpdateEmbedRequest;
import com.daniu.service.DocumentService;
import com.daniu.service.WorkspaceService;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档服务impl
 *
 * @author FangDaniu
 * @since 2024/06/08
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AnythingllmConstant anythingllmConstant;

    @Resource
    private WorkspaceService workspaceService;

    @Override
    public DocumentsResponse.LocalFiles getAllDocuments() {

        ResponseEntity<DocumentsResponse> response = restTemplate.exchange(
                anythingllmConstant.documentUrl + "s",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        ThrowUtils.throwIf(response.getBody() == null, new BusinessException(ErrorCode.OPERATION_ERROR, "获取文档失败"));

        return response.getBody().getLocalFiles();
    }

    @Override
    public WorkspaceGetResponse removeAllDocuments(String workspaceName) {
        WorkspaceUpdateEmbedRequest request = new WorkspaceUpdateEmbedRequest();

        List<String> adds = new ArrayList<>();
        List<String> deletes = new ArrayList<>();

        workspaceService.getWorkspaceByName(workspaceName).getWorkspace()
                .getDocuments().forEach(document -> deletes.add(document.getDocpath()));

        return getWorkspaceGetResponse(workspaceName, request, adds, deletes);
    }

    @Override
    public DocumentResponseWrapper uploadDocument(File file) {
        FileSystemResource fileResource = new FileSystemResource(file);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body);

        ResponseEntity<DocumentResponseWrapper> response = restTemplate.exchange(
                anythingllmConstant.documentUrl + "/upload",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        DocumentResponseWrapper responseBody = response.getBody();
        ThrowUtils.throwIf(responseBody == null, new BusinessException(ErrorCode.OPERATION_ERROR, "Response body is null"));
        return responseBody;
    }

    @Override
    public WorkspaceGetResponse addAllDocuments(String workspaceName) {
        WorkspaceUpdateEmbedRequest request = new WorkspaceUpdateEmbedRequest();

        List<String> adds = new ArrayList<>();
        List<String> deletes = new ArrayList<>();

        DocumentsResponse.LocalFiles documents = getAllDocuments();
        documents.getItems().forEach(item -> item.getItems()
                .forEach(documentItem -> adds.add(item.getName() + "/" + documentItem.getName())));

        return getWorkspaceGetResponse(workspaceName, request, adds, deletes);
    }

    private WorkspaceGetResponse getWorkspaceGetResponse(String workspaceName, WorkspaceUpdateEmbedRequest request, List<String> adds, List<String> deletes) {
        request.setAdds(adds);
        request.setDeletes(deletes);

        HttpEntity<WorkspaceUpdateEmbedRequest> requestEntity = new HttpEntity<>(request);

        ResponseEntity<WorkspaceGetResponse> response = restTemplate.exchange(
                anythingllmConstant.workspaceUrl + "/" + workspaceName + "/update-embeddings",
                HttpMethod.POST,
                requestEntity,
                WorkspaceGetResponse.class
        );

        return response.getBody();
    }

}




