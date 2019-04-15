package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;
import com.example.budgetapp.mvp.view.fragment.UpdateUnitFragmentView;
import com.example.budgetapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class UpdateUnitFragmentPresenter extends MvpPresenter<UpdateUnitFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Unit unit;

    @Inject
    Router router;
    @Inject
    UnitStorage unitStorage;

    public UpdateUnitFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int unitId) {
        disposables.add(unitStorage.getUnit(unitId)
                .observeOn(scheduler)
                .subscribe(category -> {
                    UpdateUnitFragmentPresenter.this.unit = category;
                    setUnitFieldsSelection();
                }));
    }

    private void setUnitFieldsSelection() {
        getViewState().setUnitName(unit.getName());
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void updateUnit() {
        getViewState().getData();
    }

    public void updateUnit(Unit unit) {
        unit.setId(this.unit.getId());
        disposables.add(unitStorage.addUnit(unit)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void deleteUnit(){
        disposables.add(unitStorage.deleteUnit(unit)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }
}
