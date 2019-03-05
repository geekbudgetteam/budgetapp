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

    public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ProjectRowView {

        TextView projectNameView;
        private Project project;

        ProjectHolder(View itemView) {
            super(itemView);
            projectNameView = itemView.findViewById(R.id.project_name);

        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void setProject(Project project) {
            this.project = project;
            projectNameView.setText(project.getName());
        }
    }
}
