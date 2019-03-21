package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface TransactionStorage {

    Completable addTransaction(Transaction transaction);

    Maybe<Transaction> getTransaction(int id);

    Flowable<List<Transaction>> getTransactionsList();

    Flowable<List<TransactionDetail>> getTransactionDetailsList();

    Completable updateTransaction(Transaction transaction);

    Completable deleteTransaction(Transaction transaction);
}
