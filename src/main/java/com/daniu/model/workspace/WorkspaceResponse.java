package com.daniu.model.workspace;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class WorkspaceResponse implements Serializable {

    private int id;

    private String name;

    private String slug;

    private String vectorTag;

    private OffsetDateTime createdAt;

    private Double openAiTemp;

    private int openAiHistory;

    private OffsetDateTime lastUpdatedAt;

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

    @Serial
    private static final long serialVersionUID = 1L;
}

