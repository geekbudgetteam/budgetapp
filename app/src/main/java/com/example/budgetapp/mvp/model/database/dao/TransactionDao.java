package com.example.budgetapp.mvp.model.database.dao;

import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE id = :id")
    Maybe<Transaction> getTransaction(int id);

    @Query("SELECT * FROM transactions")
    Flowable<List<Transaction>> getTransactionsList();

    @Query("SELECT * FROM transactions")
    Flowable<List<TransactionDetail>> getTransactionDetailsList();

    @Delete
    Completable deleteTransaction(Transaction transaction);

}
