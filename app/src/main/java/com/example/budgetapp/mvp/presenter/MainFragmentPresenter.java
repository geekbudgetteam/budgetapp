package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.view.MainFragmentView;

import java.util.ArrayList;

@InjectViewState
public class MainFragmentPresenter extends MvpPresenter<MainFragmentView> {

    public MainFragmentPresenter() {
    }

    public void getTotalAmount(){
        Integer totalAmount = 2523000;
        getViewState().setTotalAmount(totalAmount);
    }

    public void getTransaction(){
        ArrayList<String> transactions = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            transactions.add("Транзакция" + i);
        }
        getViewState().setTransactions(transactions);
    }

    public void setAddTransactionFragent(){
        getViewState().showAddTransactionFragment();
    }
}
