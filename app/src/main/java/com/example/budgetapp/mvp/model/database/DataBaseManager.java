package com.example.budgetapp.mvp.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectElementStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DataBaseManager implements TransactionStorage, CategoryStorage, UnitStorage, ProjectStorage, ProjectElementStorage {

    private static DataBaseManager instance;
    private DataBaseHelper dataBaseHelper;

    private DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    //  TODO: заменить на di
    public static DataBaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseManager(context);
        }
        return instance;
    }

    @Override
    public Observable<Boolean> addTransaction(Transaction transaction) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.insert(DataBaseSchema.TransactionsTable.TABLE_NAME,
                    null,
                    DataBaseSchema.TransactionsTable.getContentValues(transaction));
            database.close();
            if (success != -1) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Transaction> getTransaction(int id) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            Cursor cursor = database.query(DataBaseSchema.TransactionsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.TransactionsTable.ID_COLUMN,
                            DataBaseSchema.TransactionsTable.PROJECT_COLUMN,
                            DataBaseSchema.TransactionsTable.CATEGORY_COLUMN,
                            DataBaseSchema.TransactionsTable.DATE_COLUMN,
                            DataBaseSchema.TransactionsTable.AMOUNT_COLUMN},
                    DataBaseSchema.TransactionsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor.moveToNext()) {
                e.onNext(DataBaseSchema.TransactionsTable.parseCursor(cursor));
            } else {
                e.onError(new RuntimeException("Database does not contain the transaction"));
            }
            cursor.close();
            database.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Transaction>> getTransactionsList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<Transaction> transactions = new ArrayList<>();
            Cursor cursor = database.query(DataBaseSchema.TransactionsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.TransactionsTable.ID_COLUMN,
                            DataBaseSchema.TransactionsTable.PROJECT_COLUMN,
                            DataBaseSchema.TransactionsTable.CATEGORY_COLUMN,
                            DataBaseSchema.TransactionsTable.DATE_COLUMN,
                            DataBaseSchema.TransactionsTable.AMOUNT_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                transactions.add(DataBaseSchema.TransactionsTable.parseCursor(cursor));
            }
            cursor.close();
            database.close();
            e.onNext(transactions);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateTransaction(Transaction transaction) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.update(DataBaseSchema.TransactionsTable.TABLE_NAME,
                    DataBaseSchema.TransactionsTable.getContentValues(transaction),
                    DataBaseSchema.TransactionsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(transaction.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> deleteTransaction(Transaction transaction) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.delete(DataBaseSchema.TransactionsTable.TABLE_NAME,
                    DataBaseSchema.TransactionsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(transaction.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> addCategory(Category category) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.insert(DataBaseSchema.CategoriesTable.TABLE_NAME,
                    null,
                    DataBaseSchema.CategoriesTable.getContentValues(category));
            database.close();
            if (success != -1) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Category> getCategory(int id) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

            Cursor cursor = database.query(DataBaseSchema.CategoriesTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.CategoriesTable.ID_COLUMN,
                            DataBaseSchema.CategoriesTable.NAME_COLUMN},
                    DataBaseSchema.CategoriesTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor.moveToNext()) {
                e.onNext(DataBaseSchema.CategoriesTable.parseCursor(cursor));
            } else {
                e.onError(new RuntimeException("Database does not contain the category"));
            }
            cursor.close();
            database.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Category>> getCategoriesList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<Category> categories = new ArrayList<>();
            Cursor cursor = database.query(DataBaseSchema.CategoriesTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.CategoriesTable.ID_COLUMN,
                            DataBaseSchema.CategoriesTable.NAME_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                categories.add(DataBaseSchema.CategoriesTable.parseCursor(cursor));
            }
            cursor.close();
            database.close();

            e.onNext(categories);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateCategory(Category category) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.update(DataBaseSchema.CategoriesTable.TABLE_NAME,
                    DataBaseSchema.CategoriesTable.getContentValues(category),
                    DataBaseSchema.CategoriesTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(category.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> deleteCategory(Category category) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.delete(DataBaseSchema.CategoriesTable.TABLE_NAME,
                    DataBaseSchema.CategoriesTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(category.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> addUnit(Unit unit) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.insert(DataBaseSchema.UnitsTable.TABLE_NAME,
                    null,
                    DataBaseSchema.UnitsTable.getContentValues(unit));
            database.close();
            if (success != -1) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Unit> getUnit(int id) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();

            Cursor cursor = database.query(DataBaseSchema.UnitsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.UnitsTable.ID_COLUMN,
                            DataBaseSchema.UnitsTable.NAME_COLUMN},
                    DataBaseSchema.UnitsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor.moveToNext()) {
                e.onNext(DataBaseSchema.UnitsTable.parseCursor(cursor));
            } else {
                e.onError(new RuntimeException("Database does not contain the unit"));
            }
            cursor.close();
            database.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Unit>> getUnitsList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<Unit> units = new ArrayList<>();
            Cursor cursor = database.query(DataBaseSchema.UnitsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.UnitsTable.ID_COLUMN,
                            DataBaseSchema.UnitsTable.NAME_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                units.add(DataBaseSchema.UnitsTable.parseCursor(cursor));
            }
            cursor.close();
            database.close();

            e.onNext(units);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateUnit(Unit unit) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.update(DataBaseSchema.UnitsTable.TABLE_NAME,
                    DataBaseSchema.UnitsTable.getContentValues(unit),
                    DataBaseSchema.UnitsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(unit.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> deleteUnit(Unit unit) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.delete(DataBaseSchema.UnitsTable.TABLE_NAME,
                    DataBaseSchema.UnitsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(unit.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> addProject(Project project) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.insert(DataBaseSchema.ProjectsTable.TABLE_NAME,
                    null,
                    DataBaseSchema.ProjectsTable.getContentValues(project));
            database.close();
            if (success != -1) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Project> getProject(int id) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            Cursor cursor = database.query(DataBaseSchema.ProjectsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.ProjectsTable.ID_COLUMN,
                            DataBaseSchema.ProjectsTable.TYPE_COLUMN,
                            DataBaseSchema.ProjectsTable.NAME_COLUMN,
                            DataBaseSchema.ProjectsTable.VARIABLE_COLUMN,
                            DataBaseSchema.ProjectsTable.PERIOD_COLUMN,
                            DataBaseSchema.ProjectsTable.START_PERIOD_COLUMN,
                            DataBaseSchema.ProjectsTable.FINISH_PERIOD_COLUMN},
                    DataBaseSchema.ProjectsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor.moveToNext()) {
                e.onNext(DataBaseSchema.ProjectsTable.parseCursor(cursor));
            } else {
                e.onError(new RuntimeException("Database does not contain the project"));
            }
            cursor.close();
            database.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Project>> getProjectsList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<Project> projects = new ArrayList<>();
            Cursor cursor = database.query(DataBaseSchema.ProjectsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.ProjectsTable.ID_COLUMN,
                            DataBaseSchema.ProjectsTable.TYPE_COLUMN,
                            DataBaseSchema.ProjectsTable.NAME_COLUMN,
                            DataBaseSchema.ProjectsTable.VARIABLE_COLUMN,
                            DataBaseSchema.ProjectsTable.PERIOD_COLUMN,
                            DataBaseSchema.ProjectsTable.START_PERIOD_COLUMN,
                            DataBaseSchema.ProjectsTable.FINISH_PERIOD_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                projects.add(DataBaseSchema.ProjectsTable.parseCursor(cursor));
            }
            cursor.close();
            database.close();

            e.onNext(projects);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateProject(Project project) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.update(DataBaseSchema.ProjectsTable.TABLE_NAME,
                    DataBaseSchema.ProjectsTable.getContentValues(project),
                    DataBaseSchema.ProjectsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(project.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> deleteProject(Project project) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.delete(DataBaseSchema.ProjectsTable.TABLE_NAME,
                    DataBaseSchema.ProjectsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(project.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> addProjectElement(ProjectElement projectElement) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.insert(DataBaseSchema.ProjectElementsTable.TABLE_NAME,
                    null,
                    DataBaseSchema.ProjectElementsTable.getContentValues(projectElement));
            database.close();
            if (success != -1) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<ProjectElement> getProjectElement(int id) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            Cursor cursor = database.query(DataBaseSchema.ProjectElementsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.ProjectElementsTable.ID_COLUMN,
                            DataBaseSchema.ProjectElementsTable.PROJECT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.CATEGORY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.UNIT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.QUANTITY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MONITORED_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MINIMAL_QUANTITY_COLUMN},
                    DataBaseSchema.ProjectElementsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor.moveToNext()) {
                e.onNext(DataBaseSchema.ProjectElementsTable.parseCursor(cursor));
            } else {
                e.onError(new RuntimeException("Database does not contain the project element"));
            }
            cursor.close();
            database.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<ProjectElement>> getProjectElementsList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<ProjectElement> projectElements = new ArrayList<>();
            Cursor cursor = database.query(DataBaseSchema.ProjectElementsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.ProjectElementsTable.ID_COLUMN,
                            DataBaseSchema.ProjectElementsTable.PROJECT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.CATEGORY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.UNIT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.QUANTITY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MONITORED_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MINIMAL_QUANTITY_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                projectElements.add(DataBaseSchema.ProjectElementsTable.parseCursor(cursor));
            }
            cursor.close();
            database.close();
            e.onNext(projectElements);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateProjectElement(ProjectElement projectElement) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.update(DataBaseSchema.TransactionsTable.TABLE_NAME,
                    DataBaseSchema.ProjectElementsTable.getContentValues(projectElement),
                    DataBaseSchema.ProjectElementsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(projectElement.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> deleteProjectElement(ProjectElement projectElement) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.delete(DataBaseSchema.ProjectElementsTable.TABLE_NAME,
                    DataBaseSchema.ProjectElementsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(projectElement.getId())}
            );
            database.close();
            if (success > 0) {
                e.onNext(true);
            } else {
                e.onNext(false);
            }
            e.onComplete();
        });
    }
}
