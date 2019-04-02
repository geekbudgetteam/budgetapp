package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE id = :id")
    Maybe<Transaction> getTransaction(int id);

    @Query("SELECT * FROM transactions")
    Flowable<List<Transaction>> getTransactionsList();

    //    @Query("SELECT * FROM transactions")
    @Query("SELECT transactions.id, " +
            "projects.name AS projectName, " +
            "categories.name AS categoryName, transactions.date, transactions.amount " +
            "FROM transactions, projects, categories " +
            "WHERE projects.id = transactions.project_id " +
            "AND categories.id = transactions.category_id")
    Flowable<List<TransactionDetail>> getTransactionDetailsList();

    @Delete
    void deleteTransaction(Transaction transaction);

}
