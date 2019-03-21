package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.model.entity.Project;

import java.util.List;

public interface IProjectsSpinnerPresenter {

    int getProjectsCount();

    Project getProject(int position);

    List<Project> getProjectsList();
}
