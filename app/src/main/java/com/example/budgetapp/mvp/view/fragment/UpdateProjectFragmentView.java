package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface UpdateProjectFragmentView extends MvpView {

    void setProjectTypeSpinnerSelection(int type);

    void setProjectNameSelection(String projectNameSelection);

    void setProjectDurationSpinnerSelection(int durationSpinnerSelection);

    void setProjectPeriodSpinnerSelection(int periodSpinnerSelection);

    void setProjectStartDateSelection(long startDateSelection);

    void setProjectFinishDateSelection(long finishDateSelection);

    void getData();

    void setProjectDateFieldsVisibility(boolean start, boolean finish);

    void showMessage(String message);
}
