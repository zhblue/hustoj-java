package com.hustoj.entity;

import java.sql.Timestamp;

public class News {
    private int newsId;
    private String userId;
    private String title;
    private String content;
    private Timestamp time;
    private int importance;
    private int menu;
    private String defunct;

    public News() {}

    public int getNewsId() { return newsId; }
    public void setNewsId(int newsId) { this.newsId = newsId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }
    public int getImportance() { return importance; }
    public void setImportance(int importance) { this.importance = importance; }
    public int getMenu() { return menu; }
    public void setMenu(int menu) { this.menu = menu; }
    public String getDefunct() { return defunct; }
    public void setDefunct(String defunct) { this.defunct = defunct; }
}
