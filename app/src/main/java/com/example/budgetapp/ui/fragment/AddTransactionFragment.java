package com.example.budgetapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.AddTransactionPresenter;
import com.example.budgetapp.mvp.view.AddTransactionView;
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddTransactionFragment extends MvpAppCompatFragment implements AddTransactionView {

    private final int title = R.string.add_transaction_fragment;
    private ChangeFragmentTitleListener listener;

    @BindView(R.id.type_spinner) Spinner typeSpinner;
    @BindView(R.id.project_spinner) Spinner projectSpinner;
    @BindView(R.id.category_spinner) Spinner categorySpinner;
    @BindView(R.id.amount_input) EditText amountInput;
    @BindView(R.id.add_transaction_btn) Button addBtn;

    @InjectPresenter
    AddTransactionPresenter presenter;

    @ProvidePresenter
    public AddTransactionPresenter provideAddTransactionFragmentPresenter(){
        AddTransactionPresenter presenter = new AddTransactionPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static AddTransactionFragment newInstance(){
        return new AddTransactionFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        presenter.setProjectsType();
        presenter.setCategoryType();

        goBackButton.setOnClickListener(v -> presenter.goBackToTransactionFragment());
        addTransactionButton.setOnClickListener(v -> presenter.addTransaction());
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
    }

    @Override
    public void setProjectsList(ArrayList<String> spinnerArray) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getView().getContext(), android.R.layout.simple_spinner_item,
                        spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        projectsSpinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void setCategoryList(ArrayList<String> spinnerArray) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getView().getContext(), android.R.layout.simple_spinner_item,
                        spinnerArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerArrayAdapter);
    }
}
