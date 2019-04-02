package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.budgetapp.mvp.model.entity.Project;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

    @Query("SELECT * FROM projects WHERE id = :id")
    Maybe<Project> getProject(int id);

    @Query("SELECT * FROM projects")
    Flowable<List<Project>> getProjectsList();

    @Delete
    void deleteProject(Project project);

}
