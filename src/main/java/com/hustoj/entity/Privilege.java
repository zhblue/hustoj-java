package com.hustoj.entity;

public class Privilege {
    private String userId;
    private String rightStr;
    private String valueStr;
    private String defunct;

    public Privilege() {}

    public Privilege(String userId, String rightStr, String valueStr) {
        this.userId = userId;
        this.rightStr = rightStr;
        this.valueStr = valueStr;
        this.defunct = "N";
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getRightStr() { return rightStr; }
    public void setRightStr(String rightStr) { this.rightStr = rightStr; }
    public String getValueStr() { return valueStr; }
    public void setValueStr(String valueStr) { this.valueStr = valueStr; }
    public String getDefunct() { return defunct; }
    public void setDefunct(String defunct) { this.defunct = defunct; }
}
