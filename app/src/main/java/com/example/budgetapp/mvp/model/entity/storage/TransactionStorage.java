package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Transaction;

import java.util.List;

import io.reactivex.Observable;

public interface TransactionStorage {

    Observable<Boolean> addTransaction(Transaction transaction);

    Observable<Transaction> getTransaction(int id);

    Observable<List<Transaction>> getTransactionsList();

    Observable<Boolean> updateTransaction(Transaction transaction);

    Observable<Boolean> deleteTransaction(Transaction transaction);
}
