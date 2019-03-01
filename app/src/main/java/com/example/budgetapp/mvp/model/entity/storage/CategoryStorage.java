package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Category;

import java.util.List;

import io.reactivex.Observable;

public interface CategoryStorage {

    Observable<Boolean> addCategory(Category category);

    Observable<Category> getCategory(int id);

    Observable<List<Category>> getCategoriesList();

    Observable<Boolean> updateCategory(Category category);

    Observable<Boolean> deleteCategory(Category category);
}
