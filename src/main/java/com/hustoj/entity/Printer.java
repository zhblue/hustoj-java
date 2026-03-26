package com.hustoj.entity;

import java.sql.Timestamp;

public class Printer {
    private int printerId;
    private String userId;
    private Timestamp inDate;
    private int status;
    private Timestamp workTime;
    private String printer;
    private String content;

    public Printer() {}

    public int getPrinterId() { return printerId; }
    public void setPrinterId(int printerId) { this.printerId = printerId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Timestamp getInDate() { return inDate; }
    public void setInDate(Timestamp inDate) { this.inDate = inDate; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public Timestamp getWorkTime() { return workTime; }
    public void setWorkTime(Timestamp workTime) { this.workTime = workTime; }
    public String getPrinter() { return printer; }
    public void setPrinter(String printer) { this.printer = printer; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
