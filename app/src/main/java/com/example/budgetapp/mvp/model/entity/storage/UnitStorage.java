package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Unit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface UnitStorage {

    void addUnit(Unit unit);

    Maybe<Unit> getUnit(int id);

    Flowable<List<Unit>> getUnitsList();

    void updateUnit(Unit unit);

    void deleteUnit(Unit unit);
}
