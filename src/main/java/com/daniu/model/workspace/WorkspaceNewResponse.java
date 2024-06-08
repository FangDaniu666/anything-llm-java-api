package com.daniu.model.workspace;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WorkspaceNewResponse implements Serializable {

    private WorkspaceResponse workspace;

    private String message;

    @Serial
    private static final long serialVersionUID = 1L;
}

