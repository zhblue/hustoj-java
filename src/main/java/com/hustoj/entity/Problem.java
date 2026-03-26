package com.hustoj.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Problem {
    private int problemId;
    private String title;
    private String description;
    private String input;
    private String output;
    private String sampleInput;
    private String sampleOutput;
    private String spj;
    private String hint;
    private String source;
    private Timestamp inDate;
    private BigDecimal timeLimit;
    private int memoryLimit;
    private String defunct;
    private int accepted;
    private int submit;
    private int solved;
    private String remoteOj;
    private String remoteId;

    public Problem() {}

    public int getProblemId() { return problemId; }
    public void setProblemId(int problemId) { this.problemId = problemId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }
    public String getSampleInput() { return sampleInput; }
    public void setSampleInput(String sampleInput) { this.sampleInput = sampleInput; }
    public String getSampleOutput() { return sampleOutput; }
    public void setSampleOutput(String sampleOutput) { this.sampleOutput = sampleOutput; }
    public String getSpj() { return spj; }
    public void setSpj(String spj) { this.spj = spj; }
    public String getHint() { return hint; }
    public void setHint(String hint) { this.hint = hint; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Timestamp getInDate() { return inDate; }
    public void setInDate(Timestamp inDate) { this.inDate = inDate; }
    public BigDecimal getTimeLimit() { return timeLimit; }
    public void setTimeLimit(BigDecimal timeLimit) { this.timeLimit = timeLimit; }
    public int getMemoryLimit() { return memoryLimit; }
    public void setMemoryLimit(int memoryLimit) { this.memoryLimit = memoryLimit; }
    public String getDefunct() { return defunct; }
    public void setDefunct(String defunct) { this.defunct = defunct; }
    public int getAccepted() { return accepted; }
    public void setAccepted(int accepted) { this.accepted = accepted; }
    public int getSubmit() { return submit; }
    public void setSubmit(int submit) { this.submit = submit; }
    public int getSolved() { return solved; }
    public void setSolved(int solved) { this.solved = solved; }
    public String getRemoteOj() { return remoteOj; }
    public void setRemoteOj(String remoteOj) { this.remoteOj = remoteOj; }
    public String getRemoteId() { return remoteId; }
    public void setRemoteId(String remoteId) { this.remoteId = remoteId; }
}
