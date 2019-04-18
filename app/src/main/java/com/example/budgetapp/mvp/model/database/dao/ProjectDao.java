package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM projects WHERE projectType = :projectType")
    Flowable<List<Project>> getProjectsListByProjectType(int projectType);

//    Возвращает список с пустым экземпляром Project
//    @Query("SELECT projects.id, " +
//            "projects.projectType, projects.name, projects.variable, " +
//            "projects.projectPeriod, projects.startPeriod, projects.finishPeriod, " +
//            "SUM(transactions.amount) AS amount " +
//            "FROM projects " +
//            "INNER JOIN transactions ON transactions.project_id = projects.id " +
//            "WHERE projects.projectType = :projectType")
//    Flowable<List<Project>> getProjectsListWithSumByProjectType(int projectType);

    @Query("SELECT projects.id, " +
            "projects.projectType, projects.name, projects.variable, " +
            "projects.projectPeriod, projects.startPeriod, projects.finishPeriod, " +
            "(SELECT SUM(amount) FROM transactions WHERE project_id = projects.id) AS amount " +
            "FROM projects " +
            "WHERE projects.projectType = :projectType")
    Flowable<List<Project>> getProjectsListWithSumByProjectType(int projectType);

    @Update
    void updateProject(Project project);

    @Delete
    void deleteProject(Project project);

}
