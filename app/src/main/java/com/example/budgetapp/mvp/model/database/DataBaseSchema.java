package com.example.budgetapp.mvp.model.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.Unit;

public class DataBaseSchema {

    public static final class TransactionsTable {
        public static final String TABLE_NAME = "transactions";

        public static final String ID_COLUMN = "id";
        public static final String PROJECT_COLUMN = "project";
        public static final String CATEGORY_COLUMN = "category";
        public static final String DATE_COLUMN = "date";
        public static final String AMOUNT_COLUMN = "amount";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PROJECT_COLUMN + " INTEGER, " +
                        CATEGORY_COLUMN + " INTEGER, " +
                        DATE_COLUMN + " INTEGER NOT NULL, " +
                        AMOUNT_COLUMN + " REAL NOT NULL);";

        public static ContentValues getContentValues(Transaction transaction) {
            ContentValues values = new ContentValues();
            values.put(PROJECT_COLUMN, transaction.getProjectId());
            values.put(CATEGORY_COLUMN, transaction.getCategoryId());
            values.put(DATE_COLUMN, transaction.getDate());
            values.put(AMOUNT_COLUMN, transaction.getAmount());
            return values;
        }

        public static Transaction parseCursor(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));
            int projectId = cursor.getInt(cursor.getColumnIndexOrThrow(PROJECT_COLUMN));
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_COLUMN));
            long date = cursor.getLong(cursor.getColumnIndexOrThrow(DATE_COLUMN));
            float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(AMOUNT_COLUMN));
            Transaction transaction = new Transaction(projectId, categoryId, date, amount);
            transaction.setId(id);

            return transaction;
        }
    }

    public static final class CategoriesTable {
        public static final String TABLE_NAME = "categories";

        public static final String ID_COLUMN = "id";
        public static final String NAME_COLUMN = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NAME_COLUMN + " TEXT NOT NULL);";

        public static ContentValues getContentValues(Category category) {
            ContentValues values = new ContentValues();
            values.put(NAME_COLUMN, category.getName());
            return values;
        }

        public static Category parseCursor(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN));

            Category category = new Category(name);
            category.setId(id);

            return category;
        }
    }

    public static final class UnitsTable {
        public static final String TABLE_NAME = "units";

        public static final String ID_COLUMN = "id";
        public static final String NAME_COLUMN = "name";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NAME_COLUMN + " TEXT NOT NULL);";

        public static ContentValues getContentValues(Unit unit) {
            ContentValues values = new ContentValues();
            values.put(NAME_COLUMN, unit.getName());
            return values;
        }

        public static Unit parseCursor(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN));

            Unit unit = new Unit(name);
            unit.setId(id);

            return unit;
        }
    }

    public static final class ProjectsTable {
        public static final String TABLE_NAME = "projects";

        public static final String ID_COLUMN = "id";
        public static final String TYPE_COLUMN = "type";
        public static final String NAME_COLUMN = "name";
        public static final String VARIABLE_COLUMN = "variable";
        public static final String PERIOD_COLUMN = "period";
        public static final String START_PERIOD_COLUMN = "start";
        public static final String FINISH_PERIOD_COLUMN = "finish";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TYPE_COLUMN + " INTEGER NOT NULL, " +
                        NAME_COLUMN + " TEXT NOT NULL, " +
                        VARIABLE_COLUMN + " INTEGER NOT NULL, " +
                        PERIOD_COLUMN + " INTEGER NOT NULL, " +
                        START_PERIOD_COLUMN + " INTEGER NOT NULL, " +
                        FINISH_PERIOD_COLUMN + " INTEGER NOT NULL);";

        public static ContentValues getContentValues(Project project) {
            ContentValues values = new ContentValues();
            values.put(TYPE_COLUMN, project.getProjectType());
            values.put(NAME_COLUMN, project.getName());
            values.put(VARIABLE_COLUMN, project.getVariable());
            values.put(PERIOD_COLUMN, project.getProjectPeriod());
            values.put(START_PERIOD_COLUMN, project.getStartPeriod());
            values.put(FINISH_PERIOD_COLUMN, project.getFinishPeriod());
            return values;
        }

        public static Project parseCursor(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));
            int projectType = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE_COLUMN));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN));
            int variable = cursor.getInt(cursor.getColumnIndexOrThrow(VARIABLE_COLUMN));
            int projectPeriod = cursor.getInt(cursor.getColumnIndexOrThrow(PERIOD_COLUMN));
            long startPeriod = cursor.getLong(cursor.getColumnIndexOrThrow(START_PERIOD_COLUMN));
            long finishPeriod = cursor.getLong(cursor.getColumnIndexOrThrow(FINISH_PERIOD_COLUMN));
            Project project = new Project(projectType, name, variable, projectPeriod, startPeriod, finishPeriod);
            project.setId(id);

            return project;
        }
    }

    public static final class ProjectElementsTable {
        public static final String TABLE_NAME = "elements";

        public static final String ID_COLUMN = "id";
        public static final String PROJECT_COLUMN = "project";
        public static final String CATEGORY_COLUMN = "category";
        public static final String UNIT_COLUMN = "unit";
        public static final String QUANTITY_COLUMN = "quantity";
        public static final String MONITORED_COLUMN = "monitored";
        public static final String MINIMAL_QUANTITY_COLUMN = "minimal_quantity";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PROJECT_COLUMN + " INTEGER NOT NULL, " +
                        CATEGORY_COLUMN + " INTEGER, " +
                        UNIT_COLUMN + " INTEGER NOT NULL, " +
                        QUANTITY_COLUMN + " REAL NOT NULL, " +
                        MONITORED_COLUMN + " INTEGER NOT NULL, " +
                        MINIMAL_QUANTITY_COLUMN + " REAL NOT NULL);";

        public static ContentValues getContentValues(ProjectElement projectElement) {
            ContentValues values = new ContentValues();
            values.put(PROJECT_COLUMN, projectElement.getProjectId());
            values.put(CATEGORY_COLUMN, projectElement.getCategoryId());
            values.put(UNIT_COLUMN, projectElement.getUnitId());
            values.put(QUANTITY_COLUMN, projectElement.getQuantity());
            values.put(MONITORED_COLUMN, projectElement.isMonitored());
            values.put(MINIMAL_QUANTITY_COLUMN, projectElement.getMinimalQuantity());
            return values;
        }

        public static ProjectElement parseCursor(Cursor cursor) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COLUMN));
            int projectId = cursor.getInt(cursor.getColumnIndexOrThrow(PROJECT_COLUMN));
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_COLUMN));
            int unitId = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_COLUMN));
            float quantity = cursor.getFloat(cursor.getColumnIndexOrThrow(QUANTITY_COLUMN));
            int monitored = cursor.getInt(cursor.getColumnIndexOrThrow(MONITORED_COLUMN));
            float minimalQuantity = cursor.getFloat(cursor.getColumnIndexOrThrow(MINIMAL_QUANTITY_COLUMN));
            ProjectElement projectElement = new ProjectElement(projectId, categoryId, unitId, quantity, monitored, minimalQuantity);
            projectElement.setId(id);

            return projectElement;
        }
    }
}
