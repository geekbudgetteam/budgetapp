package com.example.budgetapp.mvp.model.entity.view;

public class ProjectElementDetail {
    private int id;
    private String name;
    private String projectName;
    private String categoryName;
    private String unitName;
    private float quantity;
    private float amount;
    private int monitored;
    private float minimalQuantity;

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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public int getMonitored() {
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
