package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.view.fragment.UpdateProjectFragmentView;
import com.example.budgetapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class UpdateProjectFragmentPresenter extends MvpPresenter<UpdateProjectFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Project project;

    @Inject
    Router router;
    @Inject
    ProjectStorage projectStorage;

    public UpdateProjectFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int projectId) {
        disposables.add(projectStorage.getProject(projectId)
                .observeOn(scheduler)
                .subscribe(project -> {
                    UpdateProjectFragmentPresenter.this.project = project;
                    setProjectFieldsSelection();
                }));
    }

    private void setProjectFieldsSelection(){
        getViewState().setProjectTypeSpinnerSelection(project.getProjectType());
        getViewState().setProjectNameSelection(project.getName());
        getViewState().setProjectDurationSpinnerSelection(project.getVariable());
        getViewState().setProjectPeriodSpinnerSelection(project.getProjectPeriod());
        getViewState().setProjectStartDateSelection(project.getStartPeriod());
        getViewState().setProjectFinishDateSelection(project.getFinishPeriod());
    }

    public void checkPeriodSpinnerChoice(int position) {
        switch (position) {
            case 4:
                getViewState().setProjectDateFieldsVisibility(true, false);
                break;
            case 5:
                getViewState().setProjectDateFieldsVisibility(true, true);
                break;
            default:
                getViewState().setProjectDateFieldsVisibility(false, false);
                break;
        }
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void updateProject() {
        getViewState().getData();
    }

    public void updateProject(Project project) {
        project.setId(this.project.getId());
        disposables.add(projectStorage.addProject(project)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }
}
