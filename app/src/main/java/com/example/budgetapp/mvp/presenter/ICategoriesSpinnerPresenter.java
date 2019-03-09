package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.model.entity.Category;

import java.util.List;

public interface ICategoriesSpinnerPresenter {

    int getCategoriesCount();

    Category getCategory(int position);

    List<Category> getCategoriesList();
}
