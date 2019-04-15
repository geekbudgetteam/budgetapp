package com.example.budgetapp.ui.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.presenter.EntityListPresenter;
import com.example.budgetapp.mvp.view.row.EntityRowView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitsListAdapter extends RecyclerView.Adapter<UnitsListAdapter.UnitHolder> {

    private EntityListPresenter<Unit> presenter;

    public UnitsListAdapter(EntityListPresenter presenter) {
        this.presenter = presenter;
    }

    @NotNull
    @Override
    public UnitsListAdapter.UnitHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new UnitHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.units_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull UnitsListAdapter.UnitHolder holder, int position) {
        presenter.bindEntityListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getEntitiesCount();
    }


    public class UnitHolder extends RecyclerView.ViewHolder implements EntityRowView<Unit> {

        private Unit unit;

        @BindView(R.id.unit_name) TextView unitNameView;

        UnitHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> presenter.navigateToEntity(unit));
        }

        @Override
        public void setEntity(Unit unit) {
            this.unit = unit;
            unitNameView.setText(unit.getName());
        }
    }
}