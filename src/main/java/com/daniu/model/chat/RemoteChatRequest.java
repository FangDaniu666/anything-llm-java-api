package com.daniu.model.chat;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * anything llm 聊天请求参数
 *
 * @author FangDaniu
 * @since 2024/05/13
 */
@Data
public class RemoteChatRequest implements Serializable {

    private String message;

    private String mode;

    @Serial
    private static final long serialVersionUID = 1L;
}

