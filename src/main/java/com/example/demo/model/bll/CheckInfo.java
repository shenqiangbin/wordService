package com.example.demo.model.bll;

import java.util.Date;

public class CheckInfo {
    public static final String TYPE_INIT = "init";
    public static final String TYPE_CHECK = "check";

    private int id;
    private Date checkTime;
    private String sysCode;
    private String type;
    private int syncState;
    private String syncMsg;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSyncState() {
        return syncState;
    }

    public void setSyncState(int syncState) {
        this.syncState = syncState;
    }

    public String getSyncMsg() {
        return syncMsg;
    }

    public void setSyncMsg(String syncMsg) {
        this.syncMsg = syncMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
