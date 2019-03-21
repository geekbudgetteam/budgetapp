package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.CategoryDao;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class CategoryRepository implements CategoryStorage {

    private CategoryDao categoryDao;

    @Inject
    public CategoryRepository(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Completable addCategory(Category category) {
        return categoryDao.insertCategory(category);
    }

    @Override
    public Maybe<Category> getCategory(int id) {
        return categoryDao.getCategory(id);
    }

    @Override
    public Flowable<List<Category>> getCategoriesList() {
        return categoryDao.getCategoriesList();
    }

    @Override
    public Completable updateCategory(Category category) {
        return categoryDao.insertCategory(category);
    }

    @Override
    public Completable deleteCategory(Category category) {
        return categoryDao.deleteCategory(category);
    }
}
