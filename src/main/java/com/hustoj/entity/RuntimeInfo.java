package com.hustoj.entity;

public class RuntimeInfo {
    private int solutionId;
    private String error;

    public RuntimeInfo() {}

    public int getSolutionId() { return solutionId; }
    public void setSolutionId(int solutionId) { this.solutionId = solutionId; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
