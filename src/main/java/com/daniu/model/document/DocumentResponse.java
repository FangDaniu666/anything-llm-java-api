package com.daniu.model.document;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DocumentResponse implements Serializable {
    private String id;

    private String url;

    private String title;

    private String docAuthor;

    private String description;

    private String docSource;

    private String chunkSource;

    private String published;

    private int wordCount;

    private String pageContent;

    private int tokenCountEstimate;

    private String location;

    @Serial
    private static final long serialVersionUID = 1L;
}

