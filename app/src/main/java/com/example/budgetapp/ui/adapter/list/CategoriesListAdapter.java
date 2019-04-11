package com.example.budgetapp.ui.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.presenter.EntityListPresenter;
import com.example.budgetapp.mvp.view.row.EntityRowView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.CategoryHolder> {

    private EntityListPresenter<Category> presenter;

    public CategoriesListAdapter(EntityListPresenter<Category> presenter) {
        this.presenter = presenter;
    }

    @NotNull
    @Override
    public CategoriesListAdapter.CategoryHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull CategoryHolder holder, int position) {
        presenter.bindEntityListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getEntitiesCount();
    }


    public class CategoryHolder extends RecyclerView.ViewHolder implements EntityRowView<Category> {

        private Category category;

        @BindView(R.id.category_name) TextView categoryNameView;

        CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> presenter.navigateToEntity(category));
        }

        @Override
        public void setEntity(Category category) {
            this.category = category;
            categoryNameView.setText(category.getName());
        }
    }
}