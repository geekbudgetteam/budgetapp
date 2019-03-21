package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;
import com.example.budgetapp.mvp.view.TransactionRowView;

public interface ITransactionsListPresenter {

    void bindTransactionsListRow(int pos, TransactionRowView rowView);

    int getTransactionsCount();

    void navigateToTransaction(TransactionDetail transaction);
}
