package com.daniu.controller;

import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.constant.AnythingllmConstant;
import com.daniu.exception.BusinessException;
import com.daniu.exception.ThrowUtils;
import com.daniu.model.document.DocumentResponse;
import com.daniu.model.document.DocumentResponseWrapper;
import com.daniu.model.document.DocumentsResponse;
import com.daniu.service.DocumentService;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 文档控制器
 *
 * @author FangDaniu
 * @since 2024/06/08
 */
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AnythingllmConstant anythingllmConstant;

    @Resource
    private DocumentService documentService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<DocumentsResponse.FolderItem>> listDocuments() {
        DocumentsResponse.LocalFiles documents = documentService.getAllDocuments();
        ThrowUtils.throwIf(documents == null, new BusinessException(ErrorCode.OPERATION_ERROR, "No documents found."));

        return ResultUtils.success(documents.getItems());
    }


    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<List<DocumentResponse>> uploadDocument(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        ThrowUtils.throwIf(multipartFile == null || multipartFile.isEmpty(),
                new BusinessException(ErrorCode.PARAMS_ERROR, "Invalid file provided."));

        File file = convertMultiPartToFile(multipartFile);
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

        boolean deleted = file.delete();
        ThrowUtils.throwIf(!deleted, new BusinessException(ErrorCode.OPERATION_ERROR, "File not deleted"));

        DocumentResponseWrapper responseBody = response.getBody();
        ThrowUtils.throwIf(responseBody == null, new BusinessException(ErrorCode.OPERATION_ERROR, "Response body is null"));

        return ResultUtils.success(responseBody.getDocuments());
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

}

