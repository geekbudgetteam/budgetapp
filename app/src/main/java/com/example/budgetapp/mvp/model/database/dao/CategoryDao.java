package com.example.budgetapp.mvp.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.budgetapp.mvp.model.entity.Category;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategory(Category category);

    @Query("SELECT * FROM categories WHERE id = :id")
    Maybe<Category> getCategory(int id);

    @Query("SELECT * FROM categories")
    Flowable<List<Category>> getCategoriesList();

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

}
