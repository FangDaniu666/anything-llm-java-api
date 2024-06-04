package com.daniu.model.workspace;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class WorkspaceResponseWrapper implements Serializable {

    private List<WorkspaceResponse> workspaces;

    @Serial
    private static final long serialVersionUID = 1L;
}

