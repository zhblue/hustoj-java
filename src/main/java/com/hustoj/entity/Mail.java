package com.hustoj.entity;

import java.sql.Timestamp;

public class Mail {
    private int mailId;
    private String toUser;
    private String fromUser;
    private String title;
    private String content;
    private boolean newMail;
    private int reply;
    private Timestamp inDate;
    private String defunct;

    public Mail() {}

    public int getMailId() { return mailId; }
    public void setMailId(int mailId) { this.mailId = mailId; }
    public String getToUser() { return toUser; }
    public void setToUser(String toUser) { this.toUser = toUser; }
    public String getFromUser() { return fromUser; }
    public void setFromUser(String fromUser) { this.fromUser = fromUser; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public boolean isNewMail() { return newMail; }
    public void setNewMail(boolean newMail) { this.newMail = newMail; }
    public int getReply() { return reply; }
    public void setReply(int reply) { this.reply = reply; }
    public Timestamp getInDate() { return inDate; }
    public void setInDate(Timestamp inDate) { this.inDate = inDate; }
    public String getDefunct() { return defunct; }
    public void setDefunct(String defunct) { this.defunct = defunct; }
}
