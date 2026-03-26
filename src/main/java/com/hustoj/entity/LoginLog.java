package com.hustoj.entity;

import java.sql.Timestamp;

public class LoginLog {
    private int logId;
    private String userId;
    private String password;
    private String ip;
    private Timestamp time;

    public LoginLog() {}

    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }
}
