package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.TransactionFragmentPresenter;
import com.example.budgetapp.mvp.view.TransactionFragmentView;
import com.example.budgetapp.ui.adapter.MainFragmentAdapter;

import java.util.ArrayList;

public class TransactionFragment extends MvpAppCompatFragment implements TransactionFragmentView {

    private MainFragmentAdapter adapter;
    private TextView totalAmountTextView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    public static Fragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @InjectPresenter
    TransactionFragmentPresenter transactionFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalAmountTextView = getView().findViewById(R.id.totalAmount);
        recyclerView = getView().findViewById(R.id.transactionsRecycler);
        fab = getView().findViewById(R.id.fragment_fab);

        fragmentManager = getFragmentManager();

        transactionFragmentPresenter.getTotalAmount();
        transactionFragmentPresenter.getTransaction();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionFragmentPresenter.setAddTransactionFragent();
            }
        });
    }

    @Override
    public void setTotalAmount(Integer totalAmount) {
        totalAmountTextView.setText(totalAmount + "рублей");
    }

    @Override
    public void setTransactions(ArrayList<String> transactions) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new MainFragmentAdapter(this.getActivity(), transactions);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showAddTransactionFragment() {
        fragment = new AddTransactionFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_master, fragment).commit();
    }
}
