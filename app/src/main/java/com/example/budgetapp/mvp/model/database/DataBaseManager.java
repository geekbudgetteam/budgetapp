package com.example.budgetapp.mvp.model.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

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
import com.example.budgetapp.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import io.reactivex.Observable;

public class DataBaseManager implements TransactionStorage, CategoryStorage, UnitStorage, ProjectStorage, ProjectElementStorage {

    private static DataBaseManager instance;
    private DataBaseHelper dataBaseHelper;

    private DataBaseManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);

//        test
        setTestProjects();

    }

    //  TODO: заменить на di
    public static DataBaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseManager(context);
        }
        return instance;
    }

    @SuppressLint("CheckResult")
    private void setTestProjects() {
        getProjectsList()
                .subscribe(projects -> {
                    Log.d(getClass().getName(), "Количество проектов до: " + projects.size());
                    if (projects.size() == 0) {
                        projects.add(new Project(Constants.EXPENSE, "Home budget", Constants.CONSTANT, Constants.MONTH, 0, 0));
                        projects.add(new Project(Constants.EXPENSE, "Holidays", Constants.CONSTANT, Constants.YEAR, 0, 0));
                        projects.add(new Project(Constants.EXPENSE, "8 March", Constants.CONSTANT, Constants.DATED, new GregorianCalendar(2019, Calendar.MARCH, 8).getTimeInMillis(), new GregorianCalendar(2019, Calendar.MARCH, 8).getTimeInMillis()));
                        projects.add(new Project(Constants.INCOME, "Salary", Constants.CONSTANT, Constants.MONTH, 0, 0));
                        for (Project project : projects) {
                            addProject(project).subscribe();
                        }
                    }
                    Log.d(getClass().getName(), "Количество проектов после: " + projects.size());
                });
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
                Transaction transaction = DataBaseSchema.TransactionsTable.parseCursor(cursor);
                int projectId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.TransactionsTable.PROJECT_COLUMN));
                transaction.setProjectId(getProject(projectId, database));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.TransactionsTable.CATEGORY_COLUMN));
                transaction.setCategoryId(getCategory(categoryId, database));
                e.onNext(transaction);
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
                Transaction transaction = DataBaseSchema.TransactionsTable.parseCursor(cursor);
                int projectId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.TransactionsTable.PROJECT_COLUMN));
                transaction.setProjectId(getProject(projectId, database));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.TransactionsTable.CATEGORY_COLUMN));
                transaction.setCategoryId(getCategory(categoryId, database));
                transactions.add(transaction);
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
            Category category = getCategory(id, database);
            if (category != null) {
                e.onNext(category);
            } else {
                e.onError(new RuntimeException("Database does not contain the category"));
            }
            database.close();
            e.onComplete();
        });
    }

    private Category getCategory(int id, SQLiteDatabase database){
        Category category = null;
        Cursor cursor = database.query(DataBaseSchema.CategoriesTable.TABLE_NAME,
                new String[]{
                        DataBaseSchema.CategoriesTable.ID_COLUMN,
                        DataBaseSchema.CategoriesTable.NAME_COLUMN},
                DataBaseSchema.CategoriesTable.ID_COLUMN + " = ? ",
                new String[]{String.valueOf(id)}, null, null, null
        );
        if (cursor.moveToNext()) {
            category =  DataBaseSchema.CategoriesTable.parseCursor(cursor);
        }
        cursor.close();
        return category;
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
            Unit unit = getUnit(id, database);
            database.close();
            e.onComplete();
        });
    }

    private Unit getUnit(int id, SQLiteDatabase database){
        Unit unit = null;
        Cursor cursor = database.query(DataBaseSchema.UnitsTable.TABLE_NAME,
                new String[]{
                        DataBaseSchema.UnitsTable.ID_COLUMN,
                        DataBaseSchema.UnitsTable.NAME_COLUMN},
                DataBaseSchema.UnitsTable.ID_COLUMN + " = ? ",
                new String[]{String.valueOf(id)}, null, null, null
        );
        if (cursor.moveToNext()) {
            unit = DataBaseSchema.UnitsTable.parseCursor(cursor);
        }
        cursor.close();
        return unit;
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
            Project project = getProject(id, database);
            if (project != null) {
                e.onNext(project);
            } else {
                e.onError(new RuntimeException("Database does not contain the project"));
            }
            database.close();
            e.onComplete();
        });
    }

    private Project getProject(int id, SQLiteDatabase database){
        Project project = null;
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
            project = DataBaseSchema.ProjectsTable.parseCursor(cursor);
        }
        cursor.close();
        return project;
    }

    @Override
    public synchronized Observable<List<Project>> getProjectsList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<Project> projects = new ArrayList<>();
            Cursor cursor = null;
            try {
                cursor = database.query(DataBaseSchema.ProjectsTable.TABLE_NAME,
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

            } catch (SQLiteException ex) {
                e.onError(ex);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
                database.close();
            }

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
                            DataBaseSchema.ProjectElementsTable.NAME_COLUMN,
                            DataBaseSchema.ProjectElementsTable.PROJECT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.CATEGORY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.UNIT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.QUANTITY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.AMOUNT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MONITORED_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MINIMAL_QUANTITY_COLUMN},
                    DataBaseSchema.ProjectElementsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor.moveToNext()) {
                ProjectElement projectElement = DataBaseSchema.ProjectElementsTable.parseCursor(cursor);
                int projectId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.ProjectElementsTable.PROJECT_COLUMN));
                projectElement.setProjectId(getProject(projectId, database));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.ProjectElementsTable.CATEGORY_COLUMN));
                projectElement.setCategoryId(getCategory(categoryId, database));
                int unitId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.ProjectElementsTable.UNIT_COLUMN));
                projectElement.setUnitId(getUnit(unitId, database));
                e.onNext(projectElement);
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
                            DataBaseSchema.ProjectElementsTable.NAME_COLUMN,
                            DataBaseSchema.ProjectElementsTable.PROJECT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.CATEGORY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.UNIT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.QUANTITY_COLUMN,
                            DataBaseSchema.ProjectElementsTable.AMOUNT_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MONITORED_COLUMN,
                            DataBaseSchema.ProjectElementsTable.MINIMAL_QUANTITY_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                ProjectElement projectElement = DataBaseSchema.ProjectElementsTable.parseCursor(cursor);
                int projectId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.ProjectElementsTable.PROJECT_COLUMN));
                projectElement.setProjectId(getProject(projectId, database));
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.ProjectElementsTable.CATEGORY_COLUMN));
                projectElement.setCategoryId(getCategory(categoryId, database));
                int unitId = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseSchema.ProjectElementsTable.UNIT_COLUMN));
                projectElement.setUnitId(getUnit(unitId, database));
                projectElements.add(projectElement);
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
