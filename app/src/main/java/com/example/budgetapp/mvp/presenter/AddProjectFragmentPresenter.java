package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.view.AddProjectFragmentView;
import com.example.budgetapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class AddProjectFragmentPresenter extends MvpPresenter<AddProjectFragmentView> {

    @Inject
    Router router;
    @Inject
    ProjectStorage projectStorage;
    private Scheduler scheduler;
    private Disposable disposable;

    public AddProjectFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
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
                break;
        }
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void addProject() {
        getViewState().getData();
    }

    public void addProject(Project project) {
        disposable = projectStorage.addProject(project)
                .observeOn(scheduler)
                .subscribe(this::onBack);
    }

    public void onBack() {
        router.exit();
    }
}
