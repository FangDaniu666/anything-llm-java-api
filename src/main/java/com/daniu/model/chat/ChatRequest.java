package com.daniu.model.chat;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

/**
 * 聊天请求参数
 *
 * @author FangDaniu
 * @since 2024/05/13
 */
@Data
public class ChatRequest implements Serializable {

    private String message;

    private String mode;

    private String workspace;

    @Serial
    private static final long serialVersionUID = 1L;
}

