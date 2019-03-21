package com.example.budgetapp.mvp.model.database;

import com.example.budgetapp.mvp.model.database.dao.CategoryDao;
import com.example.budgetapp.mvp.model.database.dao.ProjectDao;
import com.example.budgetapp.mvp.model.database.dao.ProjectElementDao;
import com.example.budgetapp.mvp.model.database.dao.TransactionDao;
import com.example.budgetapp.mvp.model.database.dao.UnitDao;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Category.class, Project.class, ProjectElement.class,
        Transaction.class, Unit.class}, views = {TransactionDetail.class}, version = 1)
public abstract class BudgetDatabase extends RoomDatabase {

    public abstract CategoryDao getCategoryDao();
    public abstract ProjectDao getProjectDao();
    public abstract ProjectElementDao getProjectElementDao();
    public abstract TransactionDao getTransactionDao();
    public abstract UnitDao getUnitDao();
}
