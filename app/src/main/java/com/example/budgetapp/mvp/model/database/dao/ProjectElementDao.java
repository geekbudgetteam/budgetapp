package com.example.budgetapp.mvp.model.database.dao;

import com.example.budgetapp.mvp.model.entity.ProjectElement;

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
public interface ProjectElementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertProjectElement(ProjectElement projectElement);

    @Query("SELECT * FROM elements WHERE id = :id")
    Maybe<ProjectElement> getProjectElement(int id);

    @Query("SELECT * FROM elements")
    Flowable<List<ProjectElement>> getProjectElementsList();

    @Delete
    Completable deleteProjectElement(ProjectElement projectElement);

}
