package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class ProjectElement implements Serializable {

    private int id;
    private String name;
    private Project project;
    private Category category;
    private Unit unit;
    private float quantity;
    private float amount;
    private int monitored;
    private float minimalQuantity;

    public ProjectElement(String name, float quantity, float amount, int monitored, float minimalQuantity) {
        this.quantity = quantity;
        this.amount = amount;
        this.monitored = monitored;
        this.minimalQuantity = minimalQuantity;
    }

    public ProjectElement(String name, Project project, Category category, Unit unit, float quantity, float amount, int monitored, float minimalQuantity) {
        this(name, quantity, amount, monitored, minimalQuantity);
        this.project = project;
        this.category = category;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int isMonitored() {
        return monitored;
    }

    public void setMonitored(int monitored) {
        this.monitored = monitored;
    }

    public float getMinimalQuantity() {
        return minimalQuantity;
    }

    public void setMinimalQuantity(float minimalQuantity) {
        this.minimalQuantity = minimalQuantity;
    }
}
