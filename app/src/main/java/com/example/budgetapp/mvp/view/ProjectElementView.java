package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ProjectElementView extends MvpView {

    void updateData();

    @StateStrategyType(SkipStrategy.class)
    void getData();

    void setMinimumQuantityVisible(boolean monitored);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);
}
