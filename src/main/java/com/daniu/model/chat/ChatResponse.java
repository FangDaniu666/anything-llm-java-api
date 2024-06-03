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

    private List<String> sources;

    @Serial
    private static final long serialVersionUID = 1L;

}
