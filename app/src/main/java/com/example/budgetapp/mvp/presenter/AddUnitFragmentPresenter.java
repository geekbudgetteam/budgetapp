package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;
import com.example.budgetapp.mvp.view.fragment.AddUnitFragmentView;
import com.example.budgetapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class AddUnitFragmentPresenter extends MvpPresenter<AddUnitFragmentView> {

    private Scheduler scheduler;
    private Disposable disposable;

    @Inject
    Router router;
    @Inject
    UnitStorage unitStorage;


    public AddUnitFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void addUnit() {
        getViewState().getData();
    }

    public void addUnit(Unit unit) {
        disposable = unitStorage.addUnit(unit)
                .observeOn(scheduler)
                .subscribe(this::onBack);
    }

    public void onBack() {
        router.exit();
    }
}
