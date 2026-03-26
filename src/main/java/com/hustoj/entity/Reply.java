package com.hustoj.entity;

import java.sql.Timestamp;

public class Reply {
    private int rid;
    private String authorId;
    private Timestamp time;
    private String content;
    private int topicId;
    private int status;
    private String ip;

    public Reply() {}

    public int getRid() { return rid; }
    public void setRid(int rid) { this.rid = rid; }
    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
}
