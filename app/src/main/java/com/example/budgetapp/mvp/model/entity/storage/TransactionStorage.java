package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface TransactionStorage {

    void addTransaction(Transaction transaction);

    Maybe<Transaction> getTransaction(int id);

    Flowable<List<Transaction>> getTransactionsList();

    Flowable<List<TransactionDetail>> getTransactionDetailsList();

    void updateTransaction(Transaction transaction);

    void deleteTransaction(Transaction transaction);
}
