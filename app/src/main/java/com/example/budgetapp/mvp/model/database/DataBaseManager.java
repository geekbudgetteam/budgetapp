package com.example.budgetapp.mvp.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Record;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.RecordStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class DataBaseManager implements RecordStorage, CategoryStorage {

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
    public Observable<Boolean> addRecord(Record record) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.insert(DataBaseSchema.RecordsTable.TABLE_NAME,
                    null,
                    DataBaseSchema.RecordsTable.getContentValues(record));
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
    public Observable<List<Record>> getRecordsList() {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getReadableDatabase();
            List<Record> records = new ArrayList<>();
            Cursor cursor = database.query(DataBaseSchema.RecordsTable.TABLE_NAME,
                    new String[]{
                            DataBaseSchema.RecordsTable.ID_COLUMN,
                            DataBaseSchema.RecordsTable.PROJECT_COLUMN,
                            DataBaseSchema.RecordsTable.CATEGORY_COLUMN,
                            DataBaseSchema.RecordsTable.DATE_COLUMN,
                            DataBaseSchema.RecordsTable.AMOUNT_COLUMN},
                    null, null, null, null, null
            );
            while (cursor.moveToNext()) {
                records.add(DataBaseSchema.RecordsTable.parseCursor(cursor));
            }
            cursor.close();
            database.close();
            Collections.sort(records, (first, second) -> {
                if (first.getDate() > second.getDate()) {
                    return -1;
                } else if (first.getDate() < second.getDate()) {
                    return 1;
                } else {
                    return 0;
                }
            });
            e.onNext(records);
            e.onComplete();
        });
    }

    @Override
    public Observable<Boolean> updateRecord(Record record) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.update(DataBaseSchema.RecordsTable.TABLE_NAME,
                    DataBaseSchema.RecordsTable.getContentValues(record),
                    DataBaseSchema.RecordsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(record.getId())}
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
    public Observable<Boolean> deleteRecord(Record record) {
        return Observable.create(e -> {
            SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
            long success = database.delete(DataBaseSchema.RecordsTable.TABLE_NAME,
                    DataBaseSchema.RecordsTable.ID_COLUMN + " = ? ",
                    new String[]{String.valueOf(record.getId())}
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
}
