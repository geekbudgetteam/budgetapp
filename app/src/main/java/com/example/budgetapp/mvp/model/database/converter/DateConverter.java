package com.example.budgetapp.mvp.model.database.converter;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

public class DateConverter {
    @TypeConverter
    public Date convertStoredValueToDate(Long value) {
        return new Date(value);
    }

    @TypeConverter
    public Long convertDateToStoredValue(@NonNull Date date) {
        return date.getTime();
    }
}
