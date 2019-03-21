package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface TransactionFragmentView extends MvpView {

    void updateTransactionsList();

    void updateTotalAmount(int amount);
}
