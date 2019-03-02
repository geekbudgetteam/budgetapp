package com.example.budgetapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainFragmentView extends MvpView {

    void setTotalAmount(Integer totalAmount);
    void setTransactions(ArrayList<String> transactions);
}
