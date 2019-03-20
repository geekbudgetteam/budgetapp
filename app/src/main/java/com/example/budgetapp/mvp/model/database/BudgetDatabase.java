package com.example.budgetapp.mvp.model.database;

import com.example.budgetapp.mvp.model.dao.CategoryDao;
import com.example.budgetapp.mvp.model.dao.ProjectDao;
import com.example.budgetapp.mvp.model.dao.ProjectElementDao;
import com.example.budgetapp.mvp.model.dao.TransactionDao;
import com.example.budgetapp.mvp.model.dao.UnitDao;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.Unit;

import androidx.room.Database;

@Database(entities = {Category.class, Project.class, ProjectElement.class,
        Transaction.class, Unit.class}, version = 1)
public abstract class BudgetDatabase {

    public abstract CategoryDao getCategoryDao();
    public abstract ProjectDao getProjectDao();
    public abstract ProjectElementDao getProjectElementDao();
    public abstract TransactionDao getTransactionDao();
    public abstract UnitDao getUnitDao();
}
