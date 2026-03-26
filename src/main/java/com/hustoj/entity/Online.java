package com.hustoj.entity;

public class Online {
    private String hash;
    private String ip;
    private String ua;
    private String refer;
    private int lastMove;
    private Integer firstTime;
    private String uri;

    public Online() {}

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getUa() { return ua; }
    public void setUa(String ua) { this.ua = ua; }
    public String getRefer() { return refer; }
    public void setRefer(String refer) { this.refer = refer; }
    public int getLastMove() { return lastMove; }
    public void setLastMove(int lastMove) { this.lastMove = lastMove; }
    public Integer getFirstTime() { return firstTime; }
    public void setFirstTime(Integer firstTime) { this.firstTime = firstTime; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
