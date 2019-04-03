package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.view.ProjectRowView;
import com.example.budgetapp.mvp.view.ProjectsListView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.utils.Constants;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class ProjectsPresenter extends MvpPresenter<ProjectsListView> {

    private Scheduler scheduler;
    private Disposable disposable;
    private IProjectsListPresenter projectsListPresenter = new ProjectsListPresenter();
    private List<Project> projects = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    ProjectStorage projectStorage;

    public ProjectsPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public IProjectsListPresenter getProjectsListPresenter() {
        return projectsListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadProjects(int fragmentType) {
        disposable = projectStorage.getProjectsList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Throwable> throwableFlowable) throws Exception {
                        return throwableFlowable.take(3).delay(1, TimeUnit.SECONDS);
                    }
                })
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
                .subscribe(new Consumer<List<Project>>() {
                    @Override
                    public void accept(List<Project> projects) throws Exception {
                        ProjectsPresenter.this.projects = projects;
                        ProjectsPresenter.this.getViewState().updateProjectsList();
                    }
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

        @Override
        public void navigateToProject(Project project) {
            router.navigateTo(new Screens.FamilyBudgetPresenterScreen());
        }
    }
}
