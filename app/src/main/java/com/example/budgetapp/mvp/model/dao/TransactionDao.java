package com.example.budgetapp.mvp.model.dao;

import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Transaction;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE id = :id")
    Flowable<Transaction> getTransaction(int id);

    @Query("SELECT * FROM transactions")
    Flowable<List<Transaction>> getTransactionsList();

    @Delete
    Completable deleteTransaction(Transaction transaction);

}
