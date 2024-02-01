package com.example.demo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Msger {

    private final Logger log = LoggerFactory.getLogger(Msger.class);

    private String jobKey;

    public Msger(String jobKey) {
        this.jobKey = jobKey;
    }

    public void showMsg(String format, Object... args) {
        String msg = String.format(format, args);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        msg = Thread.currentThread().toString().replace("Thread[", "[线程") + " " + time + " : " + msg;
        // 这里的 msg 包括所有 job1 的任务，我们需要显示指定 定时任务 的信息，所以需要把 jobKey 传递过去，根据 jobKey 来看指定的 job 信息
        try {
            MyWebSocket.sendInfo(jobKey, msg);
        } catch (IOException e) {
            log.error("msger error:", e);
        }
    }
}
