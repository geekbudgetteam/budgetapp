package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.model.entity.view.ProjectElementDetail;
import com.example.budgetapp.mvp.view.row.ProjectElementRowView;

public interface IProjectElementsListPresenter {

    void bindProjectElementListRow(int pos, ProjectElementRowView rowView);

    int getProjectElementsCount();

    void navigateToProjectElement(ProjectElementDetail projectElement);
}
