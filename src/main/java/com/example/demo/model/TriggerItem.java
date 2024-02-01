package com.example.demo.model;

import java.util.Date;

public class TriggerItem {

    private String group;
    private String name;
    private String state;
    private String stateDesc;

    private String type;
    private String cron;

    private Date startTime;
    private Date endTime;
    private Date nextFireTime;
    private Date previousFireTime;

    public TriggerItem(String group, String name, String state) {
        this.group = group;
        this.name = name;
        this.state = state;
        //                STATE_BLOCKED     4   阻塞
        //                STATE_COMPLETE     2   完成
        //                STATE_ERROR     3   错误
        //                STATE_NONE     -1   不存在
        //                STATE_NORMAL     0  正常
        //                STATE_PAUSED     1  暂停
        switch (state) {
            case "BLOCKED":
                stateDesc = "阻塞中";
                break;
            case "COMPLETE":
                stateDesc = "已完成";
                break;
            case "ERROR":
                stateDesc = "错误";
                break;
            case "NONE":
                stateDesc = "不存在";
                break;
            case "NORMAL":
                stateDesc = "已启动";
                break;
            case "PAUSED":
                stateDesc = "暂停中";
                break;
        }
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
