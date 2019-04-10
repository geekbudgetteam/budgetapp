package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface TransactionFragmentView extends MvpView {

    void setTransactionType(String type);

    void setProjectName(String projectName);

    void setCategoryName(String categoryName);

    void setTransactionAmount(String amount);

    void setTransactionDate(String date);
}
