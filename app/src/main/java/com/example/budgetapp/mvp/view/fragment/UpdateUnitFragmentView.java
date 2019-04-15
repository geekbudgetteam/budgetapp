package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface UpdateUnitFragmentView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setUnitName(String unitName);

    void getData();

    void showMessage(String message);
}
