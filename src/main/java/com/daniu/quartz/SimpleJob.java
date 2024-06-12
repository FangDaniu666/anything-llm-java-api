package com.daniu.quartz;

import com.daniu.constant.CommonConstant;
import com.daniu.model.document.DocumentResponseWrapper;
import com.daniu.service.DocumentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SimpleJob是一个简单定时任务体，用于打印出当前线程名、当前时间及当前调用次数
 *
 * @author FangDaniu
 * @since 2024/06/01
 */
@Slf4j
public class SimpleJob extends QuartzJobBean {

    @Resource
    private DocumentService documentService;

    private final static AtomicInteger counter = new AtomicInteger(1);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LocalDateTime now = LocalDateTime.now();
        String name = Thread.currentThread().getName();
        log.info("threadName = \"{}\", the {}th execution, time = \"{}\"", name, counter.getAndIncrement(), now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        File folder = new File(CommonConstant.DEFAULT_ASSETS_PATH + "/documents");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        DocumentResponseWrapper response = documentService.
                                uploadDocument(file);
                        log.info("Uploaded document: {} {}", file.getName(), response.isSuccess());
                        if (response.isSuccess()) {
                            boolean deleted = file.delete();
                            if (deleted) {
                                log.info("Deleted file: {}", file.getName());
                            } else {
                                log.info("Failed to delete file: {}", file.getName());
                            }
                        }
                    }
                }
            } else {
                log.info("The documents folder is empty or an error occurred.");
            }
        } else {
            log.info("The documents folder does not exist or is not a directory.");
        }

    }
}
