package com.hustoj.entity;

import java.sql.Timestamp;

public class Balloon {
    private int balloonId;
    private String userId;
    private int sid;
    private int cid;
    private int pid;
    private int status;

    public Balloon() {}

    public int getBalloonId() { return balloonId; }
    public void setBalloonId(int balloonId) { this.balloonId = balloonId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public int getSid() { return sid; }
    public void setSid(int sid) { this.sid = sid; }
    public int getCid() { return cid; }
    public void setCid(int cid) { this.cid = cid; }
    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
