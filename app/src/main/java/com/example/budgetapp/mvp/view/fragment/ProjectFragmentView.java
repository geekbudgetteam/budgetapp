package com.example.budgetapp.mvp.view.fragment;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = SkipStrategy.class)
public interface ProjectFragmentView extends MvpView {

    void setProjectType(String type);

    void setProjectName(String projectName);

    void setProjectDuration(String projectDuration);

    void setProjectPeriod(String projectPeriod);

    void updateProjectElementsList();

    void showMessage(String message);
}
