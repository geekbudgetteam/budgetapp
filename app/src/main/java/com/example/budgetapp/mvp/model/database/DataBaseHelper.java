package com.example.budgetapp.mvp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "budget.db";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(DataBaseSchema.TransactionsTable.CREATE);
            db.execSQL(DataBaseSchema.CategoriesTable.CREATE);
            db.execSQL(DataBaseSchema.UnitsTable.CREATE);
            db.execSQL(DataBaseSchema.ProjectsTable.CREATE);
            db.execSQL(DataBaseSchema.ProjectElementsTable.CREATE);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
