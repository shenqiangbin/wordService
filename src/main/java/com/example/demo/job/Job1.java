package com.example.demo.job;

import com.example.demo.websocket.MyWebSocket;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

public class Job1 extends QuartzJobBean implements InterruptableJob {

    private boolean interrupt = false;
    private String jobKey = "";

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        // 通过 jobExecutionContext 来传递参数

        jobKey = jobExecutionContext.getJobDetail().getKey().toString();

        showMsg("job1 start ");

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String abcVal = jobDataMap.getString("abc");
        showMsg(abcVal);

        for (int i = 0; i< 35; i++){

            if(interrupt)
                break;

            try {
                Thread.sleep(1000 * 2);
                showMsg(" job1 process " + abcVal);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        showMsg("job1 end");
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        this.interrupt = true;
    }

    public void showMsg(String msg) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        msg = Thread.currentThread().toString().replace("Thread[schedulerFactoryBean_Worker", "[线程") + " " + time + " : " + msg;
        try {
            // 这里的 msg 包括所有 job1 的任务，我们需要显示指定 定时任务 的信息，所以需要把 jobKey 传递过去，根据 jobKey 来看指定的 job 信息
            MyWebSocket.sendInfo(jobKey, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(msg);
    }
}
