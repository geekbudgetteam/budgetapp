package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName = "transactions",
        foreignKeys = {
                @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "project_id", onDelete = SET_NULL),
                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id", onDelete = SET_NULL)
        })
public class Transaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "project_id")
    private int projectId;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    private long date;
    private float amount;

    public Transaction(long date, float amount) {
        this.date = date;
        this.amount = amount;
    }

    public Transaction(int projectId, int categoryId, long date, float amount) {
        this(date, amount);
        this.projectId = projectId;
        this.categoryId = categoryId;
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
