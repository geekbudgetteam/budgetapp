package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.TransactionDao;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class TransactionRepository implements TransactionStorage {

    private TransactionDao transactionDao;

    @Inject
    public TransactionRepository(TransactionDao transactionDao){
        this.transactionDao = transactionDao;
    }

    @Override
    public Completable addTransaction(Transaction transaction) {
        return Completable.fromAction(() -> transactionDao.insertTransaction(transaction))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Transaction> getTransaction(int id) {
        return transactionDao.getTransaction(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<TransactionDetail> getTransactionDetail(int id) {
        return transactionDao.getTransactionDetail(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<Transaction>> getTransactionsList() {
        return transactionDao.getTransactionsList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<TransactionDetail>> getTransactionDetailsList() {
        return transactionDao.getTransactionDetailsList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateTransaction(Transaction transaction) {
        return Completable.fromAction(() -> transactionDao.insertTransaction(transaction))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteTransaction(Transaction transaction) {
        return Completable.fromAction(() -> transactionDao.deleteTransaction(transaction))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteTransaction(int id) {
        return Completable.fromAction(() -> transactionDao.deleteTransaction(id))
                .subscribeOn(Schedulers.io());
    }
}
