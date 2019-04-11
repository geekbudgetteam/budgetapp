package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface UpdateCategoryFragmentView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setCategoryName(String categoryName);

    void getData();

    void showMessage(String message);
}
