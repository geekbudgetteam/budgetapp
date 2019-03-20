package com.example.budgetapp.mvp.model.dao;

import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Unit;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface UnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUnit(Unit unit);

    @Query("SELECT * FROM units WHERE id = :id")
    Flowable<Unit> getUnit(int id);

    @Query("SELECT * FROM units")
    Flowable<List<Unit>> getUnitsList();

    @Delete
    Completable deleteUnit(Unit unit);

}
