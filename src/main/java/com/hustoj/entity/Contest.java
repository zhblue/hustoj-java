package com.hustoj.entity;

import java.sql.Timestamp;

public class Contest {
    private int contestId;
    private String title;
    private Timestamp startTime;
    private Timestamp endTime;
    private String defunct;
    private String description;
    private int private_;
    private int langMask;
    private String password;
    private int contestType;
    private String subnet;
    private String userId;

    public Contest() {}

    public int getContestId() { return contestId; }
    public void setContestId(int contestId) { this.contestId = contestId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Timestamp getStartTime() { return startTime; }
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public Timestamp getEndTime() { return endTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public String getDefunct() { return defunct; }
    public void setDefunct(String defunct) { this.defunct = defunct; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPrivate_() { return private_; }
    public void setPrivate_(int private_) { this.private_ = private_; }
    public int getLangMask() { return langMask; }
    public void setLangMask(int langMask) { this.langMask = langMask; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getContestType() { return contestType; }
    public void setContestType(int contestType) { this.contestType = contestType; }
    public String getSubnet() { return subnet; }
    public void setSubnet(String subnet) { this.subnet = subnet; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public boolean isRunning() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return now.after(startTime) && now.before(endTime);
    }

    public boolean isEnded() {
        return new Timestamp(System.currentTimeMillis()).after(endTime);
    }

    public boolean isNotStarted() {
        return new Timestamp(System.currentTimeMillis()).before(startTime);
    }
}
