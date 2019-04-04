package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.UnitDao;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class UnitRepository implements UnitStorage {

    private UnitDao unitDao;

    @Inject
    public UnitRepository(UnitDao unitDao) {
        this.unitDao = unitDao;
    }

    @Override
    public Completable addUnit(Unit unit) {
        return Completable.fromAction(() -> unitDao.insertUnit(unit))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Unit> getUnit(int id) {
        return unitDao.getUnit(id);
    }

    @Override
    public Flowable<List<Unit>> getUnitsList() {
        return unitDao.getUnitsList();
    }

    @Override
    public Completable updateUnit(Unit unit) {
        return Completable.fromAction(() -> unitDao.insertUnit(unit))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteUnit(Unit unit) {
        return Completable.fromAction(() -> unitDao.deleteUnit(unit))
                .subscribeOn(Schedulers.io());
    }
}
