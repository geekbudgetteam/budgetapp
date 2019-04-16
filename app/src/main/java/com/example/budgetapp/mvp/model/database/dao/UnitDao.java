package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.budgetapp.mvp.model.entity.Unit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface UnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnit(Unit unit);

    @Query("SELECT * FROM units WHERE id = :id")
    Maybe<Unit> getUnit(int id);

    @Query("SELECT * FROM units")
    Flowable<List<Unit>> getUnitsList();

    @Update
    void updateUnit(Unit unit);

    @Delete
    void deleteUnit(Unit unit);

}
