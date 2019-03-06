package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.AddTransactionFragmentPresenter;
import com.example.budgetapp.mvp.presenter.DeveloperFragmentPresenter;
import com.example.budgetapp.mvp.view.AddTransactionView;

public class AddTransactionFragment extends MvpAppCompatFragment implements AddTransactionView {
    @InjectPresenter
    AddTransactionFragmentPresenter addTransactionFragmentPresenter;

    public AddTransactionFragment() {
    }

    @ProvidePresenter
    public AddTransactionFragmentPresenter provideAddTransactionFragmentPresenter(){
        return new AddTransactionFragmentPresenter();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_transaction, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
