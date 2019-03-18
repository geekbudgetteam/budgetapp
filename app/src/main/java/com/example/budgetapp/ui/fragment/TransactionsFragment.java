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
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.TransactionFragmentPresenter;
import com.example.budgetapp.mvp.view.TransactionFragmentView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.ui.activity.MainActivity;
import com.example.budgetapp.ui.adapter.MainFragmentAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class TransactionsFragment extends MvpAppCompatFragment implements TransactionFragmentView {

    private final int title = R.string.transactions_fragment;

    private MainFragmentAdapter adapter;
    private TextView totalAmountTextView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Inject
    Router router;

    public static Fragment newInstance(){
        TransactionsFragment fragment = new TransactionsFragment();
        return fragment;
    }

    @InjectPresenter
    TransactionFragmentPresenter transactionFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setToolbarTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalAmountTextView = getView().findViewById(R.id.totalAmount);
        recyclerView = getView().findViewById(R.id.transactionsRecycler);
        fab = getView().findViewById(R.id.fragment_fab);
        setFABOnClickListener();


        transactionFragmentPresenter.getTotalAmount();
        transactionFragmentPresenter.getTransaction();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).showHamburgerIcon();
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
    }

    public void setFABOnClickListener() {
        fab.setOnClickListener(v -> {
            router.navigateTo(new Screens.AddTransactionFragmentScreen()); //TODO projectID передана заглушка
        });
    }
}
