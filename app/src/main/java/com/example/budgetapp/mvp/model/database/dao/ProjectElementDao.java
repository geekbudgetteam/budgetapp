package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.view.ProjectElementDetail;

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

    @Query("SELECT elements.id, elements.name, " +
            "projects.name AS projectName, " +
            "categories.name AS categoryName, " +
            "units.name AS unitName, " +
            "elements.quantity, elements.amount, " +
            "elements.monitored, elements.minimal_quantity AS minimalQuantity " +
            "FROM elements, projects, categories, units " +
            "WHERE projects.id = elements.project_id " +
            "AND categories.id = elements.category_id " +
            "AND units.id = elements.unit_id")
    Flowable<List<ProjectElementDetail>> getProjectElementDetailsList();

    @Query("SELECT elements.id, elements.name, " +
            "projects.name AS projectName, " +
            "categories.name AS categoryName, " +
            "units.name AS unitName, " +
            "elements.quantity, elements.amount, " +
            "elements.monitored, elements.minimal_quantity AS minimalQuantity " +
            "FROM elements, projects, categories, units " +
            "WHERE elements.project_id = :projectId " +
            "AND projects.id = elements.project_id " +
            "AND categories.id = elements.category_id " +
            "AND units.id = elements.unit_id")
    Flowable<List<ProjectElementDetail>> getProjectElementDetailsListByProject(int projectId);

    @Delete
    void deleteProjectElement(ProjectElement projectElement);

}
