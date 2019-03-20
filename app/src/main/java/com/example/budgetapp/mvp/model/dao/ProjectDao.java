package com.example.budgetapp.mvp.model.dao;

import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertProject(Project project);

    @Query("SELECT * FROM projects WHERE id = :id")
    Flowable<Project> getProject(int id);

    @Query("SELECT * FROM projects")
    Flowable<List<Project>> getProjectsList();

    @Delete
    Completable deleteProject(Project project);

}
