package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.database.DataBaseManager;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.view.ProjectRowView;
import com.example.budgetapp.mvp.view.ProjectsListView;
import com.example.budgetapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectsPresenter extends MvpPresenter<ProjectsListView> {

    private Scheduler scheduler;
    private DataBaseManager dbManager;
    private Disposable disposable;

    private IProjectsListPresenter projectsListPresenter = new ProjectsListPresenter();
    private List<Project> projects = new ArrayList<>();

    public ProjectsPresenter(Scheduler scheduler, DataBaseManager dbManager) {
        this.scheduler = scheduler;
        this.dbManager = dbManager;
    }

    public IProjectsListPresenter getProjectsListPresenter() {
        return projectsListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadProjects(int fragmentType) {
        disposable = dbManager.getProjectsList()
                .subscribeOn(Schedulers.io())
                .flatMapIterable(new Function<List<Project>, List<Project>>() {
                    @Override
                    public List<Project> apply(List<Project> projects) throws Exception {
                        return projects;
                    }
                })
                .filter(new Predicate<Project>() {
                    @Override
                    public boolean test(Project project) throws Exception {
                        boolean returnValue = false;
                        switch (fragmentType) {
                            case Constants.EXPENSE_PROJECTS:
                                returnValue = project.getProjectType() == Constants.EXPENSE;
                                break;
                            case Constants.INCOME_PROJECTS:
                                returnValue = project.getProjectType() == Constants.INCOME;
                                break;
                        }
                        return returnValue;
                    }
                })
                .toList()
                .observeOn(scheduler)
                .subscribe(projects -> {
                    ProjectsPresenter.this.projects = projects;
                    ProjectsPresenter.this.getViewState().updateProjectsList();
                });
    }

    class ProjectsListPresenter implements IProjectsListPresenter {

        @Override
        public void bindProjectsListRow(int pos, ProjectRowView rowView) {
            if (projects != null) {
                rowView.setProject(projects.get(pos));
            }
        }

        @Override
        public int getProjectsCount() {
            return projects.size();
        }
    }
}
