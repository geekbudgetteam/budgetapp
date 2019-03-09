package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ProjectElementView extends MvpView {

    void updateData();

    void getData();

    void setMinimumQuantityVisible(boolean monitored);

    void showMessage(String message);

    void actionComplete();


}
