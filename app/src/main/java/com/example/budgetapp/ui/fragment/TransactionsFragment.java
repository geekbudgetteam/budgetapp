package com.example.budgetapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.TransactionsPresenter;
import com.example.budgetapp.mvp.view.TransactionFragmentView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;
import com.example.budgetapp.ui.adapter.TransactionsListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.terrakok.cicerone.Router;

public class TransactionsFragment extends MvpAppCompatFragment implements TransactionFragmentView {

    private final int title = R.string.transactions_fragment;
    private ChangeFragmentTitleListener listener;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangeFragmentTitleListener){
            listener = (ChangeFragmentTitleListener)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ButterKnife.bind(view);
        return view;
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
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
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
}
