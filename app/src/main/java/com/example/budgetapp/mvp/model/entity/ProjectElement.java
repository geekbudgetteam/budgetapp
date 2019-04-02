package com.example.budgetapp.mvp.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.SET_NULL;

@Entity(tableName = "elements",
        foreignKeys = {
                @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "project_id", onDelete = SET_NULL),
                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id", onDelete = SET_NULL)
        })
public class ProjectElement implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "project_id", index = true)
    private int projectId;
    @ColumnInfo(name = "category_id", index = true)
    private int categoryId;
    @ColumnInfo(name = "unit_id")
    private int unitId;
    private float quantity;
    private float amount;
    private int monitored;
    @ColumnInfo(name = "minimal_quantity")
    private float minimalQuantity;

    public ProjectElement(String name, int projectId, int categoryId, int unitId, float quantity, float amount, int monitored, float minimalQuantity) {
        this.name = name;
        this.projectId = projectId;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.quantity = quantity;
        this.amount = amount;
        this.monitored = monitored;
        this.minimalQuantity = minimalQuantity;
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
