package com.hustoj.entity;

public class Topic {
    private int tid;
    private String title;
    private int status;
    private int topLevel;
    private Integer cid;
    private int pid;
    private String authorId;

    public Topic() {}

    public int getTid() { return tid; }
    public void setTid(int tid) { this.tid = tid; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public int getTopLevel() { return topLevel; }
    public void setTopLevel(int topLevel) { this.topLevel = topLevel; }
    public Integer getCid() { return cid; }
    public void setCid(Integer cid) { this.cid = cid; }
    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }
    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
}
