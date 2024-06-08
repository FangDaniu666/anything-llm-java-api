package com.daniu.model.document;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class DocumentResponseWrapper implements Serializable {
    private boolean success;

    private String error;

    private List<DocumentResponse> documents;

    @Serial
    private static final long serialVersionUID = 1L;
}
