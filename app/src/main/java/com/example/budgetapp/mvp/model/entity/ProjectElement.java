package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class ProjectElement implements Serializable {

    private int id;
    private Project project;
    private Category category;
    private Unit unit;
    private float quantity;
    private int monitored;
    private float minimalQuantity;

    public ProjectElement(float quantity, int monitored, float minimalQuantity) {
        this.quantity = quantity;
        this.monitored = monitored;
        this.minimalQuantity = minimalQuantity;
    }

    public ProjectElement(Project project, Category category, Unit unit, float quantity, int monitored, float minimalQuantity) {
        this(quantity, monitored, minimalQuantity);
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
