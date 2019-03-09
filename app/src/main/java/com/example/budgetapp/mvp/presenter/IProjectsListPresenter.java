package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.view.ProjectRowView;

public interface IProjectsListPresenter {

    void bindProjectsListRow(int pos, ProjectRowView rowView);

    int getProjectsCount();

    void navigateToProject(Project project);
}
