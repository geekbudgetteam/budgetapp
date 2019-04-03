package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface AddTransactionView extends MvpView {

    void updateData();

    @StateStrategyType(SkipStrategy.class)
    void getData();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);
}
