package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class Record implements Serializable {

    private int id;
    private String projectName;
    private String categoryName;
    private long date;
    private float amount;

    public Record(String projectName, String categoryName, long date, float amount) {
        this.projectName = projectName;
        this.categoryName = categoryName;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
