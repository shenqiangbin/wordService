package com.example.demo.task;

import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@DisallowConcurrentExecution
@Component
public class TestTask {

    private static final Logger log = LoggerFactory.getLogger(TestTask.class);

    @Scheduled(fixedRate=5000)
    public void task1(){
        log.error("task1 run");
        for(int i=0; i< 10; i++){
            log.error("task1 count:" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.error("task1 run end");
    }

    @Scheduled(cron="0/5 * * * * ? ")
    public void task2(){
        log.error("task2 run");
        for(int i=0; i< 10; i++){
            log.error("task2 count:" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.error("task2 run end");
    }
}
