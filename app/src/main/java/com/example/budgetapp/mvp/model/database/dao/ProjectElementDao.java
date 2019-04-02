package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.budgetapp.mvp.model.entity.ProjectElement;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface ProjectElementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjectElement(ProjectElement projectElement);

    @Query("SELECT * FROM elements WHERE id = :id")
    Maybe<ProjectElement> getProjectElement(int id);

    @Query("SELECT * FROM elements")
    Flowable<List<ProjectElement>> getProjectElementsList();

    @Delete
    void deleteProjectElement(ProjectElement projectElement);

}
