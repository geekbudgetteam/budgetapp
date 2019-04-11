package com.example.budgetapp.mvp.presenter;

import com.example.budgetapp.mvp.view.row.EntityRowView;

public interface EntityListPresenter<T> {

    void bindEntityListRow(int pos, EntityRowView<T> rowView);

    int getEntitiesCount();

    void navigateToEntity(T entity);
}
