package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectElementStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.model.entity.view.ProjectElementDetail;
import com.example.budgetapp.mvp.view.fragment.ProjectFragmentView;
import com.example.budgetapp.mvp.view.row.ProjectElementRowView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class ProjectFragmentPresenter extends MvpPresenter<ProjectFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private IProjectElementsListPresenter projectElementsListPresenter = new ProjectElementsListPresenter();
    private Project project;
    private List<ProjectElementDetail> projectElements = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    ProjectStorage projectStorage;
    @Inject
    ProjectElementStorage projectElementStorage;

    public ProjectFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int projectId) {
        disposables.add(projectStorage.getProject(projectId)
                .observeOn(scheduler)
                .subscribe(project -> {
                    ProjectFragmentPresenter.this.project = project;
                    setProjectFieldsSelection();
                    disposables.add(projectElementStorage.getProjectElementDetailsListByProject(projectId)
                            .observeOn(scheduler)
                            .subscribe(projectElements -> {
                                ProjectFragmentPresenter.this.projectElements = projectElements;
                                getViewState().updateProjectElementsList();
                            }));
                }));
    }

    private void setProjectFieldsSelection() {
        String projectType = null;
        switch (project.getProjectType()) {
            case Constants.EXPENSE:
                projectType = Constants.EXPENSE_STRING;
                break;
            case Constants.INCOME:
                projectType = Constants.EXPENSE_STRING;
                break;
        }
        getViewState().setProjectType(projectType);
        getViewState().setProjectName(project.getName());
        String projectDuration = null;
        switch (project.getVariable()) {
            case Constants.CONSTANT:
                projectDuration = Constants.CONSTANT_STRING;
                break;
            case Constants.INCOME:
                projectDuration = Constants.VARIABLE_STRING;
                break;
        }
        getViewState().setProjectDuration(projectDuration);
        StringBuilder periodBuilder = new StringBuilder();
        periodBuilder.append(Constants.PERIOD_FIELD);
        switch (project.getProjectPeriod()) {
            case Constants.DAY:
                periodBuilder.append(Constants.DAY_STRING);
                break;
            case Constants.WEEK:
                periodBuilder.append(Constants.WEEK_STRING);
                break;
            case Constants.MONTH:
                periodBuilder.append(Constants.MONTH_STRING);
                break;
            case Constants.YEAR:
                periodBuilder.append(Constants.YEAR_STRING);
                break;
            case Constants.DATE:
                periodBuilder.append(Constants.DATE_FORMAT.format(project.getStartPeriod()));
                break;
            case Constants.PERIOD:
                periodBuilder.append(Constants.DATE_FORMAT.format(project.getStartPeriod()))
                        .append(" - ")
                        .append(Constants.DATE_FORMAT.format(project.getFinishPeriod()));
                break;
        }
        getViewState().setProjectPeriod(periodBuilder.toString());
    }

    public IProjectElementsListPresenter getProjectElementsListPresenter() {
        return projectElementsListPresenter;
    }

    public void fabAction() {
        router.navigateTo(new Screens.AddProjectElementFragmentScreen(project.getId()));
    }

    public void editProject(){
        router.navigateTo(new Screens.UpdateProjectFragmentScreen(project.getId()));
    }

    public void deleteProject(){
        disposables.add(projectStorage.deleteProject(project)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }

    class ProjectElementsListPresenter implements IProjectElementsListPresenter {

        @Override
        public void bindProjectElementListRow(int pos, ProjectElementRowView rowView) {
            if (projectElements != null) {
                rowView.setProjectElement(projectElements.get(pos));
            }
        }

        @Override
        public int getProjectElementsCount() {
            return projectElements.size();
        }

        @Override
        public void navigateToProjectElement(ProjectElementDetail projectElement) {
            router.navigateTo(new Screens.AddProjectElementFragmentScreen(project.getId()));
        }
    }
}
