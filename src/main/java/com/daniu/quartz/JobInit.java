package com.daniu.quartz;

import jakarta.annotation.Resource;
import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 作业初始化
 *
 * @author FangDaniu
 * @since 2024/06/01
 */
@Component
public class JobInit implements ApplicationRunner {

    private static final String ID = "Upload";

    @Resource
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity(ID)
                .storeDurably()
                .build();

        CronScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule("0/10 * * * * ? *");

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(ID + " Trigger")
                .withSchedule(scheduleBuilder)
                .startNow()
                .build();

        Set<Trigger> set = new HashSet<>();
        set.add(trigger);

        scheduler.scheduleJob(jobDetail, set, true);
    }
}


