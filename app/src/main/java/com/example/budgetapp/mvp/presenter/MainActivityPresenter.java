package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.view.MainActivityView;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {
    public MainActivityPresenter() {
    }

    public void setAddTransactionfragment(){
        getViewState().showAddTransactionFragment();
    }
}
