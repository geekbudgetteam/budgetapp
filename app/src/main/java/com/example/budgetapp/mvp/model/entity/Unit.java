package com.example.budgetapp.mvp.model.entity;

import java.io.Serializable;

public class Unit implements Serializable {

    private int id;
    private String name;

    public Unit(String name) {
        this.name = name;
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
}
