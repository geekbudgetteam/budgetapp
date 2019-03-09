package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.model.entity.Unit;

import java.util.List;

public interface IUnitsSpinnerPresenter {

    int getUnitsCount();

    Unit getUnit(int position);

    List<Unit> getUnitsList();
}
