package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.TransactionsFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.TransactionsFragmentView;
import com.example.budgetapp.ui.adapter.list.TransactionsListAdapter;

import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransactionsFragment extends BaseFragment implements TransactionsFragmentView {

    private TransactionsListAdapter adapter;

    @BindView(R.id.totalAmount) TextView totalAmountTextView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_fab) FloatingActionButton fab;

    @InjectPresenter
    TransactionsFragmentPresenter presenter;

    @ProvidePresenter
    TransactionsFragmentPresenter providePresenter() {
        TransactionsFragmentPresenter presenter = new TransactionsFragmentPresenter(AndroidSchedulers.mainThread());
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
        return R.layout.fragment_transactions;
    }

    @Override
    int getTitleRes() {
        return R.string.transactions_fragment;
    }


    public void showProgressBar(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public void updateUI() {
        presenter.loadData();
    }

    @Override
    public void updateTransactionsList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTotalAmount(float amount) {
        String value = String.format(Locale.getDefault(), "%.2f %s", amount, Objects.requireNonNull(getContext()).getResources().getString(R.string.currency));
        totalAmountTextView.setText(value);
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
