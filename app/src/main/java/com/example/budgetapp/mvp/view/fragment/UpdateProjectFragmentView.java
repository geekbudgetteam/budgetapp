package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface UpdateProjectFragmentView extends MvpView {

    void setProjectTypeSpinnerSelection(int type);

    void setProjectNameSelection(String projectNameSelection);

    void setProjectDurationSpinnerSelection(int durationSpinnerSelection);

    void setProjectPeriodSpinnerSelection(int periodSpinnerSelection);

    void setProjectStartDateSelection(long startDateSelection);

    void setProjectFinishDateSelection(long finishDateSelection);

    void setProjectDateFieldsVisibility(boolean start, boolean finish);

    @StateStrategyType(SkipStrategy.class)
    void getData();

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String message);
}
