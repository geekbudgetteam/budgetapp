package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;
import com.example.budgetapp.mvp.view.fragment.UnitsFragmentView;
import com.example.budgetapp.mvp.view.row.EntityRowView;
import com.example.budgetapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class UnitsFragmentPresenter extends MvpPresenter<UnitsFragmentView> {

    private Scheduler scheduler;
    private Disposable disposable;
    private EntityListPresenter<Unit> unitsListPresenter = new UnitsFragmentPresenter.UnitsListPresenter();
    private List<Unit> units = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    UnitStorage unitStorage;

    public UnitsFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadData(){
        disposable = unitStorage.getUnitsList()
                .observeOn(scheduler)
                .subscribe(units -> {
                    UnitsFragmentPresenter.this.units = units;
                    UnitsFragmentPresenter.this.getViewState().updateData();
                });
    }

    public EntityListPresenter<Unit> getUnitsListPresenter() {
        return unitsListPresenter;
    }

    public void fabAction(){
        router.navigateTo(new Screens.AddUnitFragmentScreen());
    }

    public void onBack() {
        router.exit();
    }

    class UnitsListPresenter implements EntityListPresenter<Unit> {

        @Override
        public void bindEntityListRow(int pos, EntityRowView rowView) {
            if (units.size() != 0) {
                rowView.setEntity(units.get(pos));
            }
        }

        @Override
        public int getEntitiesCount() {
            return units.size();
        }

        @Override
        public void navigateToEntity(Unit unit) {
            router.navigateTo(new Screens.UpdateUnitFragmentScreen(unit.getId()));
        }
    }
}
