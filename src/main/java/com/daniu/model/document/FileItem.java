package com.daniu.model.document;

import lombok.Data;

import java.util.List;

@Data
public class FileItem {
    private String name;

    private String type;

    private String id;

    private String url;

    private String title;

    private String docAuthor;

    private String description;

    private String docSource;

    private String chunkSource;

    private String published;

    private int wordCount;

    private int tokenCountEstimate;

    private boolean cached;

    private List<String> pinnedWorkspaces;

}
