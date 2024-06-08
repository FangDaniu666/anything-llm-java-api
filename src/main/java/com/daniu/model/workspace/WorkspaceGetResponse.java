package com.daniu.model.workspace;

import lombok.Data;

import java.util.List;

@Data
public class WorkspaceGetResponse {
    private Workspace workspace;

    @Data
    public static class Workspace {
        private int id;
        private String name;
        private String slug;
        private String vectorTag;
        private String createdAt;
        private Integer openAiTemp;
        private int openAiHistory;
        private String lastUpdatedAt;
        private String openAiPrompt;
        private double similarityThreshold;
        private String chatProvider;
        private String chatModel;
        private int topN;
        private String chatMode;
        private String pfpFilename;
        private String agentProvider;
        private String agentModel;
        private String queryRefusalResponse;
        private List<Document> documents;
    }

    @Data
    public static class Document {
        private int id;
        private String docId;
        private String filename;
        private String docpath;
        private int workspaceId;
        private String metadata;
        private boolean pinned;
        private String createdAt;
        private String lastUpdatedAt;
    }
}
