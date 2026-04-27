package com.hustoj.entity;

import java.sql.Timestamp;

public class Solution {
    private int solutionId;
    private int problemId;
    private String userId;
    private String nick;
    private int time;
    private int memory;
    private Timestamp inDate;
    private int result;
    private int language;
    private String ip;
    private int contestId;
    private int valid;
    private int num;
    private int codeLength;
    private Timestamp judgeTime;
    private double passRate;
    private boolean firstTime;
    private int lintError;
    private String judge;
    private String remoteOj;
    private String remoteId;

    public Solution() {}

    public int getSolutionId() { return solutionId; }
    public void setSolutionId(int solutionId) { this.solutionId = solutionId; }
    public int getProblemId() { return problemId; }
    public void setProblemId(int problemId) { this.problemId = problemId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }
    public int getTime() { return time; }
    public void setTime(int time) { this.time = time; }
    public int getMemory() { return memory; }
    public void setMemory(int memory) { this.memory = memory; }
    public Timestamp getInDate() { return inDate; }
    public void setInDate(Timestamp inDate) { this.inDate = inDate; }
    public int getResult() { return result; }
    public void setResult(int result) { this.result = result; }
    public int getLanguage() { return language; }
    public void setLanguage(int language) { this.language = language; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public int getContestId() { return contestId; }
    public void setContestId(int contestId) { this.contestId = contestId; }
    public int getValid() { return valid; }
    public void setValid(int valid) { this.valid = valid; }
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
    public int getCodeLength() { return codeLength; }
    public void setCodeLength(int codeLength) { this.codeLength = codeLength; }
    public Timestamp getJudgeTime() { return judgeTime; }
    public void setJudgeTime(Timestamp judgeTime) { this.judgeTime = judgeTime; }
    public double getPassRate() { return passRate; }
    public void setPassRate(double passRate) { this.passRate = passRate; }
    public boolean isFirstTime() { return firstTime; }
    public void setFirstTime(boolean firstTime) { this.firstTime = firstTime; }
    public int getLintError() { return lintError; }
    public void setLintError(int lintError) { this.lintError = lintError; }
    public String getJudge() { return judge; }
    public void setJudge(String judge) { this.judge = judge; }
    public String getRemoteOj() { return remoteOj; }
    public void setRemoteOj(String remoteOj) { this.remoteOj = remoteOj; }
    public String getRemoteId() { return remoteId; }
    public void setRemoteId(String remoteId) { this.remoteId = remoteId; }

    public static final int RESULT_PD = 0;   // Pending
    public static final int RESULT_PR = 1;   // Pending Rejudging
    public static final int RESULT_CI = 2;   // Compiling
    public static final int RESULT_RJ = 3;   // Running & Judging
    public static final int RESULT_AC = 4;   // Accepted
    public static final int RESULT_PE = 5;   // Presentation Error
    public static final int RESULT_WA = 6;   // Wrong Answer
    public static final int RESULT_TLE = 7;  // Time Limit Exceed
    public static final int RESULT_MLE = 8;  // Memory Limit Exceed
    public static final int RESULT_OLE = 9;  // Output Limit Exceed
    public static final int RESULT_RE = 10;  // Runtime Error
    public static final int RESULT_CE = 11;  // Compile Error
    public static final int RESULT_CO = 12; // Compile OK
    public static final int RESULT_TR = 13; // Test Run
    public static final int RESULT_MC = 14; // Manual Confirmation
    public static final int RESULT_SUBMITTING = 15; // Submitting
    public static final int RESULT_REMOTE_PENDING = 16; // Remote Pending
    public static final int RESULT_REMOTE_JUDGING = 17; // Remote Judging
}
