package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.TransactionsPresenter;
import com.example.budgetapp.mvp.view.TransactionFragmentView;
import com.example.budgetapp.ui.adapter.TransactionsListAdapter;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransactionsFragment extends BaseFragment implements TransactionFragmentView {

    private TransactionsListAdapter adapter;

    @BindView(R.id.totalAmount) TextView totalAmountTextView;
    @BindView(R.id.transactionsRecycler) RecyclerView recyclerView;
    @BindView(R.id.fragment_fab) FloatingActionButton fab;

    @InjectPresenter
    TransactionsPresenter presenter;

    @ProvidePresenter
    TransactionsPresenter providePresenter() {
        TransactionsPresenter presenter = new TransactionsPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static TransactionsFragment newInstance(){
        return new TransactionsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new TransactionsListAdapter(presenter.getTransactionsListPresenter());
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> presenter.fabAction());
        updateUI();
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_transaction;
    }

    @Override
    int getTitleRes() {
        return R.string.transactions_fragment;
    }

    public void updateUI() {
        presenter.loadTransactions();
    }

    @Override
    public void updateTransactionsList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTotalAmount(int amount) {
        totalAmountTextView.setText(String.valueOf(amount));
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();

    }
}
