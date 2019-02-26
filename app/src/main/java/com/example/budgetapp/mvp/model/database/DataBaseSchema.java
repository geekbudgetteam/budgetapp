package com.example.budgetapp.mvp.model.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.budgetapp.mvp.model.entity.Record;

public class DataBaseSchema {

    public static final class RecordsTable {
        public static final String TABLE_NAME = "records";

        public static final String ID_COLUMN = "id";
        public static final String PROJECT_COLUMN = "project";
        public static final String CATEGORY_COLUMN = "category";
        public static final String DATE_COLUMN = "date";
        public static final String AMOUNT_COLUMN = "amount";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PROJECT_COLUMN + " TEXT, " +
                        CATEGORY_COLUMN + " TEXT, " +
                        DATE_COLUMN + " INTEGER NOT NULL, " +
                        AMOUNT_COLUMN + " REAL NOT NULL);";

        public static ContentValues getContentValues(Record record) {
            ContentValues values = new ContentValues();
            values.put(PROJECT_COLUMN, record.getProjectName());
            values.put(CATEGORY_COLUMN, record.getCategoryName());
            values.put(DATE_COLUMN, record.getDate());
            values.put(AMOUNT_COLUMN, record.getAmount());
            return values;
        }

        public static Record parseCursor(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));
            String projectName = cursor.getString(cursor.getColumnIndexOrThrow(PROJECT_COLUMN));
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY_COLUMN));
            long date = cursor.getLong(cursor.getColumnIndexOrThrow(DATE_COLUMN));
            float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(AMOUNT_COLUMN));
            Record record = new Record(projectName, categoryName, date, amount);
            record.setId(id);

            return record;
        }
    }
}
