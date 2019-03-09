package com.example.budgetapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.presenter.IProjectsListPresenter;
import com.example.budgetapp.mvp.view.ProjectRowView;
import com.example.budgetapp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProjectsListAdapter extends RecyclerView.Adapter<ProjectsListAdapter.ProjectHolder> {

    private IProjectsListPresenter presenter;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

    public ProjectsListAdapter(IProjectsListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectHolder holder, int position) {
        presenter.bindProjectsListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getProjectsCount();
    }

    public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ProjectRowView {

        TextView projectNameView;
        TextView projectPeriodView;
        TextView projectAmountView;
        private Project project;

        ProjectHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this::onClick);
            projectNameView = itemView.findViewById(R.id.project_name);
            projectPeriodView = itemView.findViewById(R.id.project_period);
            projectAmountView = itemView.findViewById(R.id.project_amount);
        }

        @Override
        public void onClick(View view) {

            presenter.navigateToProject(project);
        }

        @Override
        public void setProject(Project project) {
            this.project = project;
            String name = "Наименование: " + project.getName();
            projectNameView.setText(name);
            String period = null;
            switch (project.getProjectPeriod()) {
                case Constants.WEEK:
                    period = "Период: " + Constants.WEEK_STRING;
                    break;
                case Constants.MONTH:
                    period = "Период: " + Constants.MONTH_STRING;
                    break;
                case Constants.YEAR:
                    period = "Период: " + Constants.YEAR_STRING;
                    break;
                case Constants.DATED:
                    if (project.getStartPeriod() == project.getFinishPeriod()) {
                        period = period = "Период: "
                                + sdf.format(project.getStartPeriod());
                    } else {
                        period = "Период: "
                                + sdf.format(project.getStartPeriod()
                                + " - " + sdf.format(project.getFinishPeriod()));
                    }
                    break;
            }
            projectPeriodView.setText(period);
            projectAmountView.setText(String.valueOf(project.getAmount()));
        }
    }
}
