package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class ProjectElement implements Serializable {

    private int id;
    private int projectId;
    private int categoryId;
    private int unitId;
    private float quantity;
    private int monitored;
    private float minimalQuantity;

    public ProjectElement(int projectId, int categoryId, int unitId, float quantity, int monitored, float minimalQuantity) {
        this.projectId = projectId;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.quantity = quantity;
        this.monitored = monitored;
        this.minimalQuantity = minimalQuantity;
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

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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
