package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface UpdateTransactionFragmentView extends MvpView {

    void setTransactionTypeSpinnerSelection(int type);

    void setProjectSpinnerSelection(int projectSpinnerSelection);

    void setCategorySpinnerSelection(int categorySpinnerSelection);

    void setTransactionAmountSelection(String amountSelection);

    void setTransactionDateSelection(String dateSelection);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateData();

    void getData();

    void showMessage(String message);
}
