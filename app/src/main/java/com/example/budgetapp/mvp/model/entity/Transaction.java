package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int id;
    private int projectId;
    private int categoryId;
    private long date;
    private float amount;

    public Transaction(int projectId, int categoryId, long date, float amount) {
        this.projectId = projectId;
        this.categoryId = categoryId;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
