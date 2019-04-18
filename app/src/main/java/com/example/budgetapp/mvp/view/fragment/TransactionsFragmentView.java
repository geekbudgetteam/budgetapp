package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface TransactionsFragmentView extends MvpView {

    void updateTransactionsList();

    void updateTotalAmount(float amount);

    void showProgressBar(boolean visible);
}
