package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface CategoryFragmentView extends MvpView {

    void setCategoryName(String categoryName);

    void updateTransactionsList();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);
}
