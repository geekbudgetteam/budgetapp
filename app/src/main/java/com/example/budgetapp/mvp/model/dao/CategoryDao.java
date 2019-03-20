package com.example.budgetapp.mvp.model.dao;

import com.example.budgetapp.mvp.model.entity.Category;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCategory(Category category);

    @Query("SELECT * FROM categories WHERE id = :id")
    Flowable<Category> getCategory(int id);

    @Query("SELECT * FROM categories")
    Flowable<List<Category>> getCategoriesList();

    @Delete
    Completable deleteCategory(Category category);

}
