package com.example.budgetapp.mvp.model.entity.view;

import android.arch.persistence.room.TypeConverters;

import com.example.budgetapp.mvp.model.database.converter.DateConverter;

import java.util.Date;

//("SELECT transactions.id, " +
//        "projects.name AS projectName, transactions " +
//        "categories.name AS categoryName, transactions.name, transactions.amount " +
//        "FROM transactions " +
//        "INNER JOIN projects ON transactions.project_id = projects.id" +
//        "INNER JOIN categories ON transactions.category_id = categories.id" )
public class TransactionDetail {
    private int id;
    private String projectName;
    private String categoryName;
    @TypeConverters({DateConverter.class})
    private Date date;
    private float amount;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
