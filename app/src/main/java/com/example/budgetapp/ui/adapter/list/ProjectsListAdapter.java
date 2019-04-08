package com.example.budgetapp.ui.adapter.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.presenter.IProjectsListPresenter;
import com.example.budgetapp.mvp.view.row.ProjectRowView;
import com.example.budgetapp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectsListAdapter extends RecyclerView.Adapter<ProjectsListAdapter.ProjectHolder> {

    private IProjectsListPresenter presenter;

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

    public class ProjectHolder extends RecyclerView.ViewHolder implements ProjectRowView {

        private Project project;

        @BindView(R.id.project_name)
        TextView projectNameView;
        @BindView(R.id.transaction_date)
        TextView projectPeriodView;
        @BindView(R.id.transaction_amount)
        TextView projectAmountView;

        ProjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> presenter.navigateToProject(project));
        }

        @Override
        public void setProject(Project project) {
            this.project = project;
            String name = Constants.NAME_FIELD + project.getName();
            projectNameView.setText(name);
            StringBuilder periodBuilder = new StringBuilder();
            periodBuilder.append(Constants.PERIOD_FIELD);
            switch (project.getProjectPeriod()) {
                case Constants.DAY:
                    periodBuilder.append(Constants.DAY_STRING);
                    break;
                case Constants.WEEK:
                    periodBuilder.append(Constants.WEEK_STRING);
                    break;
                case Constants.MONTH:
                    periodBuilder.append(Constants.MONTH_STRING);
                    break;
                case Constants.YEAR:
                    periodBuilder.append(Constants.YEAR_STRING);
                    break;
                case Constants.DATE:
                    periodBuilder.append(Constants.DATE_FORMAT.format(project.getStartPeriod()));
                    break;
                case Constants.PERIOD:
                    periodBuilder.append(Constants.DATE_FORMAT.format(project.getStartPeriod()))
                            .append(" - ")
                            .append(Constants.DATE_FORMAT.format(project.getFinishPeriod()));
                    break;
            }
            projectPeriodView.setText(periodBuilder.toString());
            projectAmountView.setText(String.valueOf(project.getAmount()));
        }
    }
}
