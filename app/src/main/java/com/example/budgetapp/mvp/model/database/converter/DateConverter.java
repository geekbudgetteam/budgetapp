package com.example.budgetapp.mvp.model.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

import java.util.Date;

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
