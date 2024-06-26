package com.daniu.controller;

import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.exception.ThrowUtils;
import com.daniu.model.chat.ChatRequest;
import com.daniu.model.chat.ChatResponse;
import com.daniu.model.workspace.*;
import com.daniu.service.DocumentService;
import com.daniu.service.WorkspaceService;
import jakarta.annotation.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天接口
 *
 * @author FangDaniu
 * @since 2024/05/13
 */

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {

    @Resource
    private WorkspaceService workspaceService;

    @Resource
    private DocumentService documentService;

    @GetMapping(value = "/{workspaceName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<WorkspaceGetResponse> getWorkspace(@PathVariable String workspaceName) {
        WorkspaceGetResponse workspace = workspaceService.getWorkspaceByName(workspaceName);
        return ResultUtils.success(workspace);
    }

    @GetMapping(value = "/list")
    public BaseResponse<List<WorkspaceResponse>> listWorkspaces() {

        WorkspaceResponseWrapper workspaceResponses = workspaceService.getAllWorkspace();
        ThrowUtils.throwIf(workspaceResponses == null, ErrorCode.OPERATION_ERROR, "获取工作空间列表失败");

        return ResultUtils.success(workspaceResponses.getWorkspaces());
    }


    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<WorkspaceResponse> createWorkspace(@RequestBody String name) {
        ThrowUtils.throwIf(name == null || name.isEmpty(), ErrorCode.PARAMS_ERROR, "请求参数错误");

        WorkspaceNewResponse workspaceNewResponse = workspaceService.getNewWorkspace(name);
        ThrowUtils.throwIf(workspaceNewResponse == null, ErrorCode.OPERATION_ERROR, "新建工作空间失败");

        return ResultUtils.success(workspaceNewResponse.getWorkspace());
    }

    @DeleteMapping(value = "/{workspaceName}")
    public BaseResponse<String> deleteWorkspace(@PathVariable String workspaceName) {

        ResponseEntity<String> response = workspaceService.deleteWorkspace(workspaceName);
        ThrowUtils.throwIf(response.getStatusCode().value() != 200, ErrorCode.OPERATION_ERROR, "删除工作空间失败");

        return ResultUtils.success(response.getBody());
    }

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        ThrowUtils.throwIf(chatRequest == null, ErrorCode.PARAMS_ERROR, "请求参数错误");

        ResponseEntity<ChatResponse> response = workspaceService.getChat(chatRequest);

        return ResultUtils.success(response.getBody());
    }

    @PostMapping(value = "/{workspaceName}/update-embeddings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<WorkspaceGetResponse> updateEmbeddings(@PathVariable String workspaceName) {
        WorkspaceGetResponse deleteResponse = documentService.removeAllDocuments(workspaceName);
        ThrowUtils.throwIf(deleteResponse == null, ErrorCode.OPERATION_ERROR, "删除 embeddings 失败");

        WorkspaceGetResponse addResponse = documentService.addAllDocuments(workspaceName);
        ThrowUtils.throwIf(addResponse == null, ErrorCode.OPERATION_ERROR, "更新 embeddings 失败");
        return ResultUtils.success(addResponse);
    }

}
