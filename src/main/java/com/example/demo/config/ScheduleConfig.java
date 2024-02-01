package com.example.demo.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //scheduler.setPoolSize(10);

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
//
//        //Common Thread Pool
//        ExecutorService pool = new ThreadPoolExecutor(10, 200,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10, namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        poolExecutor.setMaximumPoolSize(200);

        taskRegistrar.setScheduler(poolExecutor);


    }
}
