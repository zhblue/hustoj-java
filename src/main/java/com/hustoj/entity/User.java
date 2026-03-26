package com.hustoj.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

public class User {
    private String userId;
    private String email;
    private int submit;
    private int solved;
    private String defunct;
    private String ip;
    private Timestamp accessTime;
    private int volume;
    private int language;
    private String password;
    private Timestamp regTime;
    private LocalDate expiryDate;
    private String nick;
    private String school;
    private String groupName;
    private String activeCode;
    private int starred;

    public User() {}

    public User(String userId, String password, String email, String nick, String school) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nick = nick;
        this.school = school;
        this.defunct = "N";
        this.submit = 0;
        this.solved = 0;
        this.volume = 1;
        this.language = 1;
        this.regTime = new Timestamp(System.currentTimeMillis());
        this.expiryDate = LocalDate.of(2099, 1, 1);
        this.groupName = "";
        this.activeCode = "";
        this.starred = 0;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getSubmit() { return submit; }
    public void setSubmit(int submit) { this.submit = submit; }
    public int getSolved() { return solved; }
    public void setSolved(int solved) { this.solved = solved; }
    public String getDefunct() { return defunct; }
    public void setDefunct(String defunct) { this.defunct = defunct; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public Timestamp getAccessTime() { return accessTime; }
    public void setAccessTime(Timestamp accessTime) { this.accessTime = accessTime; }
    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }
    public int getLanguage() { return language; }
    public void setLanguage(int language) { this.language = language; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Timestamp getRegTime() { return regTime; }
    public void setRegTime(Timestamp regTime) { this.regTime = regTime; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public String getActiveCode() { return activeCode; }
    public void setActiveCode(String activeCode) { this.activeCode = activeCode; }
    public int getStarred() { return starred; }
    public void setStarred(int starred) { this.starred = starred; }
}
