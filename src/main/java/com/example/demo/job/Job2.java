package com.example.demo.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job2 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        // 通过 jobExecutionContext 来传递参数

        System.out.println("job2 start");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String abcVal = jobDataMap.getString("abc");

        System.out.println("job2 end");
    }
}
