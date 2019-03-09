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
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.presenter.IUnitsSpinnerPresenter;

public class UnitsSpinnerAdapter extends ArrayAdapter<Unit> implements SpinnerAdapter {

    private IUnitsSpinnerPresenter presenter;

    public UnitsSpinnerAdapter(Context context, IUnitsSpinnerPresenter presenter) {
        super(context, R.layout.spinner_item);
        this.presenter = presenter;

    }

    @Override
    public int getCount() {
        return presenter.getUnitsCount();
    }

    @Override
    public Unit getItem(int position) {
        return presenter.getUnit(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Unit unit = getItem(position);
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(unit.getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        Unit unit = getItem(position);
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(unit.getName());
        return label;
    }
}
