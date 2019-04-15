package com.example.budgetapp.ui.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;
import com.example.budgetapp.mvp.presenter.EntityListPresenter;
import com.example.budgetapp.mvp.view.row.EntityRowView;
import com.example.budgetapp.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.TransactionHolder> {

    private EntityListPresenter<TransactionDetail> presenter;

    public TransactionsListAdapter(EntityListPresenter<TransactionDetail> presenter) {
        this.presenter = presenter;
    }

    @NotNull
    @Override
    public TransactionHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new TransactionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TransactionHolder holder, int position) {
        presenter.bindEntityListRow(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getEntitiesCount();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder implements EntityRowView<TransactionDetail> {

        private TransactionDetail transaction;

        @BindView(R.id.project_name) TextView projectNameView;
        @BindView(R.id.category_name) TextView categoryNameView;
        @BindView(R.id.transaction_date) TextView transactionDateView;
        @BindView(R.id.transaction_amount) TextView transactionAmountView;

        TransactionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> presenter.navigateToEntity(transaction));
    }

        @Override
        public void setEntity(TransactionDetail transaction) {
            this.transaction = transaction;
            String projectName = Constants.PROJECT_FIELD + transaction.getProjectName();
            projectNameView.setText(projectName);
            String categoryName = Constants.CATEGORY_FIELD + transaction.getCategoryName();
            categoryNameView.setText(categoryName);
            transactionDateView.setText(Constants.DATE_FORMAT.format(transaction.getDate()));
            transactionAmountView.setText(String.format(Locale.getDefault(), "%.2f", transaction.getAmount()));
        }
    }
}