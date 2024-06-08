package com.daniu.model.workspace;

import lombok.Data;

import java.util.List;

@Data
public class WorkspaceUpdateEmbedRequest {
    private List<String> adds;

    private List<String> deletes;

}
