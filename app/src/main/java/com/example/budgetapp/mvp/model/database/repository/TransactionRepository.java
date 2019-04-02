package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.TransactionDao;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class TransactionRepository implements TransactionStorage {

    private TransactionDao transactionDao;

    @Inject
    public TransactionRepository(TransactionDao transactionDao){
        this.transactionDao = transactionDao;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionDao.insertTransaction(transaction);
    }

    @Override
    public Maybe<Transaction> getTransaction(int id) {
        return transactionDao.getTransaction(id);
    }

    @Override
    public Flowable<List<Transaction>> getTransactionsList() {
        return transactionDao.getTransactionsList();
    }

    @Override
    public Flowable<List<TransactionDetail>> getTransactionDetailsList() {
        return transactionDao.getTransactionDetailsList();
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        transactionDao.insertTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        transactionDao.deleteTransaction(transaction);
    }
}
