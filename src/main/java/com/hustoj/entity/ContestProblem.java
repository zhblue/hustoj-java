package com.hustoj.entity;

public class ContestProblem {
    private int problemId;
    private int contestId;
    private String title;
    private int num;
    private int cAccepted;
    private int cSubmit;

    public ContestProblem() {}

    public int getProblemId() { return problemId; }
    public void setProblemId(int problemId) { this.problemId = problemId; }
    public int getContestId() { return contestId; }
    public void setContestId(int contestId) { this.contestId = contestId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getNum() { return num; }
    public void setNum(int num) { this.num = num; }
    public int getcAccepted() { return cAccepted; }
    public void setcAccepted(int cAccepted) { this.cAccepted = cAccepted; }
    public int getcSubmit() { return cSubmit; }
    public void setcSubmit(int cSubmit) { this.cSubmit = cSubmit; }
}
