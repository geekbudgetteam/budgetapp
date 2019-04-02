package com.example.budgetapp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.presenter.ICategoriesSpinnerPresenter;

import org.jetbrains.annotations.NotNull;

public class CategoriesSpinnerAdapter extends ArrayAdapter<Category> implements SpinnerAdapter {

    private ICategoriesSpinnerPresenter presenter;

    public CategoriesSpinnerAdapter(Context context, ICategoriesSpinnerPresenter presenter) {
//        super(context, android.R.layout.simple_spinner_item);
        super(context, R.layout.spinner_item);
        this.presenter = presenter;

    }

    @Override
    public int getCount() {
        return presenter.getCategoriesCount();
    }

    @Override
    public Category getItem(int position) {
        return presenter.getCategory(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        Category category = getItem(position);
        label.setTextColor(Color.BLACK);
        label.setText(category.getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        Category category = getItem(position);
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(category.getName());
        return label;
    }
}
