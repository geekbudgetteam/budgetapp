package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface AddCategoryFragmentView extends MvpView {

    void getData();

    void showMessage(String message);
}
