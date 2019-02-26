package com.example.budgetapp.mvp.model.entity.storage;


import com.example.budgetapp.mvp.model.entity.Record;

import java.util.List;

import io.reactivex.Observable;

public interface RecordStorage {

    Observable<Boolean> addRecord(Record record);

    Observable<List<Record>> getRecordsList();

    Observable<Boolean> updateRecord(Record record);

    Observable<Boolean> deleteRecord(Record record);
}
