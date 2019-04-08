package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddProjectElementFragmentView extends MvpView {

    void updateData();

    @StateStrategyType(SkipStrategy.class)
    void getData();

    void setMinimumQuantityVisible(boolean monitored);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);
}
