package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.view.fragment.ProjectsListFragmentView;
import com.example.budgetapp.mvp.view.row.EntityRowView;
import com.example.budgetapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class ProjectsListFragmentPresenter extends MvpPresenter<ProjectsListFragmentView> {

    private Scheduler scheduler;
    private Disposable disposable;
    private EntityListPresenter<Project> projectsListPresenter = new ProjectsListPresenter();
    private List<Project> projects = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    ProjectStorage projectStorage;

    public ProjectsListFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public EntityListPresenter<Project> getProjectsListPresenter() {
        return projectsListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadProjects(int fragmentType) {
        disposable = projectStorage.getProjectsListWithSumByProjectType(fragmentType)
                .observeOn(scheduler)
                .subscribe(projects -> {
                    ProjectsListFragmentPresenter.this.projects = projects;
                    ProjectsListFragmentPresenter.this.getViewState().updateProjectsList();
                });
    }

    class ProjectsListPresenter implements EntityListPresenter<Project> {

        @Override
        public void bindEntityListRow(int pos, EntityRowView<Project> rowView) {
            if (projects.size() != 0) {
                rowView.setEntity(projects.get(pos));
            }
        }

        @Override
        public int getEntitiesCount() {
            return projects.size();
        }

        @Override
        public void navigateToEntity(Project project) {
            router.navigateTo(new Screens.ProjectFragmentScreen(project.getId()));
        }
    }
}
