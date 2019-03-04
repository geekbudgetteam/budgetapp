package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int id;
    private Project project;
    private Category category;
    private long date;
    private float amount;

    public Transaction(long date, float amount) {
        this.date = date;
        this.amount = amount;
    }

    public Transaction(Project project, Category category, long date, float amount) {
        this(date, amount);
        this.project = project;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
