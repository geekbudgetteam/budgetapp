package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface AddProjectFragmentView extends MvpView {

    void getData();

    void setProjectDateFieldsVisibility(boolean start, boolean finish);

    void showMessage(String message);
}
