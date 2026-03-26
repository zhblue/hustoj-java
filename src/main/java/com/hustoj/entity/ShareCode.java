package com.hustoj.entity;

import java.sql.Timestamp;

public class ShareCode {
    private int shareId;
    private String userId;
    private String title;
    private String shareCode;
    private String language;
    private Timestamp shareTime;

    public ShareCode() {}

    public int getShareId() { return shareId; }
    public void setShareId(int shareId) { this.shareId = shareId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getShareCode() { return shareCode; }
    public void setShareCode(String shareCode) { this.shareCode = shareCode; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Timestamp getShareTime() { return shareTime; }
    public void setShareTime(Timestamp shareTime) { this.shareTime = shareTime; }
}
