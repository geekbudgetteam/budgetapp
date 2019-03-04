package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Unit;

import java.util.List;

import io.reactivex.Observable;

public interface UnitStorage {

    Observable<Boolean> addUnit(Unit unit);

    Observable<Unit> getUnit(int id);

    Observable<List<Unit>> getUnitsList();

    Observable<Boolean> updateUnit(Unit unit);

    Observable<Boolean> deleteUnit(Unit unit);
}
