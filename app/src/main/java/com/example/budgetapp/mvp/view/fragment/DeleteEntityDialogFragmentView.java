package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
public interface DeleteEntityDialogFragmentView extends MvpView {

    void sendResult(int result);
}
