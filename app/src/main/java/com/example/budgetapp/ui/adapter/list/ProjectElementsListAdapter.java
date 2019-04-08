package com.example.budgetapp.ui.adapter.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.view.ProjectElementDetail;
import com.example.budgetapp.mvp.presenter.IProjectElementsListPresenter;
import com.example.budgetapp.mvp.view.row.ProjectElementRowView;
import com.example.budgetapp.utils.Constants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectElementsListAdapter extends RecyclerView.Adapter<ProjectElementsListAdapter.ProjectElementHolder> {

    private IProjectElementsListPresenter presenter;

    public ProjectElementsListAdapter(IProjectElementsListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProjectElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectElementHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_elements_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectElementHolder holder, int position) {
        presenter.bindProjectElementListRow(position, holder);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return presenter.getProjectElementsCount();
    }

    public class ProjectElementHolder extends RecyclerView.ViewHolder implements ProjectElementRowView {

        private ProjectElementDetail projectElement;

        @BindView(R.id.project_element_name)
        TextView projectElementNameText;
        @BindView(R.id.category_name)
        TextView categoryNameText;
        @BindView(R.id.project_element_quantity)
        TextView projectElementQuantityText;
        @BindView(R.id.project_element_amount)
        TextView projectElementAmountText;

        ProjectElementHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> presenter.navigateToProjectElement(projectElement));
        }

        @Override
        public void setProjectElement(ProjectElementDetail projectElement) {
            this.projectElement = projectElement;
            String name = Constants.NAME_FIELD + projectElement.getName();
            projectElementNameText.setText(name);
            String categoryName = Constants.CATEGORY_FIELD + projectElement.getCategoryName();
            categoryNameText.setText(categoryName);
            String projectElementQuantity = String.format(Locale.getDefault(), "%.2f %s",
                    projectElement.getQuantity(), projectElement.getUnitName());
            projectElementQuantityText.setText(projectElementQuantity);
            String projectElementAmount = String.format(Locale.getDefault(), "%.2f %s",
                    projectElement.getAmount(), Constants.CURRENCY);

            projectElementAmountText.setText(projectElementAmount);
        }
    }
}
