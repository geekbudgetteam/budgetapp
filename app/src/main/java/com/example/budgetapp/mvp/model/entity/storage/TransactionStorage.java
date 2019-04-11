package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface TransactionStorage {

    Completable addTransaction(Transaction transaction);

    Maybe<Transaction> getTransaction(int id);

    Maybe<TransactionDetail> getTransactionDetail(int id);

    Flowable<List<Transaction>> getTransactionsList();

    Flowable<List<TransactionDetail>> getTransactionDetailsList();

    Flowable<List<TransactionDetail>> getTransactionDetailsListByCategory(int categoryId);

    Completable updateTransaction(Transaction transaction);

    Completable deleteTransaction(Transaction transaction);

    Completable deleteTransaction(int id);
}
