# anything-llm-java-api

通过 spring boot 调用 AnythingLLM 的API。

## 如何使用

1. git clone https://github.com/FangDaniu666/anything-llm-java-api.git  
   或下载该仓库代码到本地。
2. 运行项目，查看 API 文档。

## API 接口

### workspace管理

#### 获取工作空间详情

```
GET /workspace/{workspaceName}
```

#### 列出所有工作空间

```
GET /workspace/list
```

#### 新建工作空间

```
POST /workspace/new
请求体: 工作空间名称
```

#### 删除工作空间

```
DELETE /workspace/{workspaceName}
```

#### 更新工作空间的 embeddings

```
POST /workspace/{workspaceName}/update-embeddings
```

### 聊天功能

#### 发送聊天请求

```
POST /workspace/chat
请求体: ChatRequest 对象
```

#### 流式聊天

```
GET /workspace/stream-chat
请求体: ChatRequest 对象
```

### 文档管理

#### 列出所有文档

```
GET /document/list
```

#### 上传文档

```
POST /document/upload
请求参数: multipartFile (文件)
```

## 注意事项

- 在 `AnythingllmConstant.java` 文件中配置 `url` 和 `token`。
- 项目需要 Java 11 及以上版本。
