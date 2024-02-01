package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class JobItem {

    private String title;
    private String group;
    private String name;
    private String jobClass;

    private List<TriggerItem> triggers = new ArrayList<>();

    public JobItem(String title, String group, String name, String jobClass) {
        this.title = title;
        this.group = group;
        this.name = name;
        this.jobClass = jobClass;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
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

    public List<TriggerItem> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<TriggerItem> triggers) {
        this.triggers = triggers;
    }
}
