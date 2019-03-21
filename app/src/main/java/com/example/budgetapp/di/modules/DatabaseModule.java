package com.example.budgetapp.di.modules;

import com.example.budgetapp.App;
import com.example.budgetapp.mvp.model.database.dao.CategoryDao;
import com.example.budgetapp.mvp.model.database.dao.ProjectDao;
import com.example.budgetapp.mvp.model.database.dao.ProjectElementDao;
import com.example.budgetapp.mvp.model.database.dao.TransactionDao;
import com.example.budgetapp.mvp.model.database.dao.UnitDao;
import com.example.budgetapp.mvp.model.database.BudgetDatabase;
import com.example.budgetapp.mvp.model.database.repository.CategoryRepository;
import com.example.budgetapp.mvp.model.database.repository.ProjectElementRepository;
import com.example.budgetapp.mvp.model.database.repository.ProjectRepository;
import com.example.budgetapp.mvp.model.database.repository.TransactionRepository;
import com.example.budgetapp.mvp.model.database.repository.UnitRepository;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectElementStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public class DatabaseModule {

    public static final String DATABASE_NAME = "budget.db";

    @Singleton
    @Provides
    BudgetDatabase provideDatabase(final App app) {
        return Room.databaseBuilder(app, BudgetDatabase.class, DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    CategoryDao provideCategoryDao(final BudgetDatabase database) {
        return database.getCategoryDao();
    }

    @Singleton
    @Provides
    CategoryStorage provideCategoryStorage(final CategoryDao categoryDao) {
        return new CategoryRepository(categoryDao);
    }

    @Singleton
    @Provides
    ProjectDao provideProjectDao(final BudgetDatabase database) {
        return database.getProjectDao();
    }

    @Singleton
    @Provides
    ProjectStorage provideProjectStorage(final ProjectDao projectDao) {
        return new ProjectRepository(projectDao);
    }

    @Singleton
    @Provides
    ProjectElementDao provideProjectElementDao(final BudgetDatabase database) {
        return database.getProjectElementDao();
    }

    @Singleton
    @Provides
    ProjectElementStorage provideProjectElementStorage(final ProjectElementDao projectElementDao) {
        return new ProjectElementRepository(projectElementDao);
    }

    @Singleton
    @Provides
    TransactionDao provideTransactionDao(final BudgetDatabase database) {
        return database.getTransactionDao();
    }

    @Singleton
    @Provides
    TransactionStorage provideTransactionStorage(final TransactionDao transactionDao) {
        return new TransactionRepository(transactionDao);
    }

    @Singleton
    @Provides
    UnitDao provideUnitDao(final BudgetDatabase database) {
        return database.getUnitDao();
    }

    @Singleton
    @Provides
    UnitStorage provideUnitStorage(final UnitDao unitDao) {
        return new UnitRepository(unitDao);
    }


}
