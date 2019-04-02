package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Category;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface CategoryStorage {

    void addCategory(Category category);

    Maybe<Category> getCategory(int id);

    Flowable<List<Category>> getCategoriesList();

    void updateCategory(Category category);

    void deleteCategory(Category category);
}
