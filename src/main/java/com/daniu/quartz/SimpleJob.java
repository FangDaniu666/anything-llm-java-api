package com.daniu.quartz;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SimpleJob是一个简单定时任务体，用于打印出当前线程名、当前时间及当前调用次数
 *
 * @author FangDaniu
 * @since 2024/06/01
 */
public class SimpleJob extends QuartzJobBean {

    private final static AtomicInteger counter = new AtomicInteger(1);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        LocalDateTime now = LocalDateTime.now();
        String name = Thread.currentThread().getName();
        System.out.println("Execute quartz \"SimpleJob\", threadName = \"" + name +
                "\", the " + counter.getAndIncrement() + "th execution, time = \"" +
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "\"");
    }
}
