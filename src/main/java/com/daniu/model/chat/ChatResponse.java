package com.daniu.model.chat;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 聊天响应
 *
 * @author FangDaniu
 * @since 2024/06/03
 */

@Data
public class ChatResponse implements Serializable {
    private String id;
    private String type;
    private boolean close;
    private String error;
    private int chatId;
    private String textResponse;
    private List<Source> sources;

    @Serial
    private static final long serialVersionUID = 1L;

    @Data
    public static class Source implements Serializable {
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
        private String text;
        private double _distance;
        private double score;

        @Serial
        private static final long serialVersionUID = 1L;
    }

}
