package com.daniu.constant;

import com.daniu.config.AnythingllmConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * anything llm 常量
 *
 * @author FangDaniu
 * @since 2024/06/03
 */
@Component
@Data
public class AnythingllmConstant {

    @Resource
    private AnythingllmConfig anythingllmConfig;

    /**
     * anything llm应用路径
     */
    public String workspaceUrl;
    public String adminUrl;
    public String authUrl;
    public String systemUrl;
    public String documentUrl;
    public String token;

    @PostConstruct
    public void init() {
        String url = anythingllmConfig.getUrl() + "/api/v1";
        this.workspaceUrl = url + "/workspace";
        this.adminUrl = url + "/admin";
        this.authUrl = url + "/auth";
        this.systemUrl = url + "/system";
        this.documentUrl = url + "/document";
        this.token = anythingllmConfig.getToken();
    }
}