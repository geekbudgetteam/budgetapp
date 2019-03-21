package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Category;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface CategoryStorage {

    Completable addCategory(Category category);

    Maybe<Category> getCategory(int id);

    Flowable<List<Category>> getCategoriesList();

    Completable updateCategory(Category category);

    Completable deleteCategory(Category category);
}
