package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class Project implements Serializable {

    private int id;
    private int projectType;
    private String name;
    private int variable;
    private int projectPeriod;
    private long startPeriod;
    private long finishPeriod;
    private float amount;

    public Project(int id) {
        this.id = id;
    }

    public Project(int projectType, String name, int variable, int projectPeriod, long startPeriod, long finishPeriod) {
        this.projectType = projectType;
        this.name = name;
        this.variable = variable;
        this.projectPeriod = projectPeriod;
        this.startPeriod = startPeriod;
        this.finishPeriod = finishPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectType() {
        return projectType;
    }

    public void setProjectType(int projectType) {
        this.projectType = projectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public int getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(int projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    public long getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(long startPeriod) {
        this.startPeriod = startPeriod;
    }

    public long getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(long finishPeriod) {
        this.finishPeriod = finishPeriod;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
