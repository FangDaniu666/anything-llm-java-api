package com.daniu.model.document;

import lombok.Data;
import java.util.List;

@Data
public class DocumentsResponse {
    private LocalFiles localFiles;

    @Data
    public static class LocalFiles {
        private String name;
        private String type;
        private List<FolderItem> items;
    }

    @Data
    public static class FolderItem {
        private String name;
        private String type;
        private List<DocumentFileItemResponse> items;
    }

}

