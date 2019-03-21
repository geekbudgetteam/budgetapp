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
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.presenter.ICategoriesSpinnerPresenter;
import com.example.budgetapp.mvp.presenter.IProjectsSpinnerPresenter;

public class ProjectsSpinnerAdapter extends ArrayAdapter<Project> implements SpinnerAdapter {

    private IProjectsSpinnerPresenter presenter;

    public ProjectsSpinnerAdapter(Context context, IProjectsSpinnerPresenter presenter) {
        super(context, R.layout.spinner_item);
        this.presenter = presenter;

    }

    @Override
    public int getCount() {
        return presenter.getProjectsCount();
    }

    @Override
    public Project getItem(int position) {
        return presenter.getProject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        Project project = getItem(position);
        label.setTextColor(Color.BLACK);
        label.setText(project.getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        Project project = getItem(position);
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(project.getName());
        return label;
    }
}
