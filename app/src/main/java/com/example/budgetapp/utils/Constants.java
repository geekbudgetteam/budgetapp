package com.example.budgetapp.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

    //    Request types
    public static final int DELETE_REQUEST = 1;

    //    Project types int
    public static final int EXPENSE = 0;
    public static final int INCOME = 1;

    //    Project types String
    public static final String EXPENSE_STRING = "Расходы";
    public static final String INCOME_STRING = "Доходы";

    //    Project variable types int
    public static final int CONSTANT = 0;
    public static final int VARIABLE = 1;

    //    Project variable types String
    public static final String CONSTANT_STRING = "Постоянный";
    public static final String VARIABLE_STRING = "Переменный";

    //    Project period types int
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int WEEK = 2;
    public static final int DAY = 3;
    public static final int DATE = 4;
    public static final int PERIOD = 5;

    //    Project period types String
    public static final String YEAR_STRING = "Год";
    public static final String MONTH_STRING = "Месяц";
    public static final String WEEK_STRING = "Неделя";
    public static final String DAY_STRING = "День";

    public static final String CURRENCY = "руб.";

    //    ProjectElement monitored types
    public static final int UNMONITORED = 0;
    public static final int MONITORED = 1;

    //    ProjectsListFragment types
    public static final int EXPENSE_PROJECTS = 0;
    public static final int INCOME_PROJECTS = 1;

    //    ProjectsList titles
    public static final String EXPENSE_TITLE = "Проекты расходов";
    public static final String INCOME_TITLE = "Проекты доходов";

    //    ProjectElement Spinner titles
    public static final String SPINNER_CHOICE = "Выбрать";
    public static final String SPINNER_ADD = "Добавить";

    //    ProjectElement Error message
    public static final String ERROR_MESSAGE = "Ошибка ввода данных в поле: ";

    //    View fields
    public static final String NAME_FIELD = "Наименование: ";
    public static final String PROJECT_FIELD = "Проект: ";
    public static final String CATEGORY_FIELD = "Категория: ";
    public static final String PERIOD_FIELD = "Период: ";
    public static final String QUANTITY_FIELD = "Количество: ";

    //    Dialog result values
    public static final int RESULT_CANCEL = 0;
    public static final int RESULT_OK = 1;

    //    Date format
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());


}
