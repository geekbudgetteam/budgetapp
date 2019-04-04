package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Unit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface UnitStorage {

    Completable addUnit(Unit unit);

    Maybe<Unit> getUnit(int id);

    Flowable<List<Unit>> getUnitsList();

    Completable updateUnit(Unit unit);

    Completable deleteUnit(Unit unit);
}
