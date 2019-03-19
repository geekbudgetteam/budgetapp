package com.example.budgetapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.database.DataBaseManager;
import com.example.budgetapp.mvp.presenter.AddTransactionFragmentPresenter;
import com.example.budgetapp.mvp.view.AddTransactionView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;

import java.util.ArrayList;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class AddTransactionFragment extends MvpAppCompatFragment implements AddTransactionView {

    private final int title = R.string.add_transaction_fragment;
    private ChangeFragmentTitleListener listener;

    private Button goBackButton;
    private Button addTransactionButton;
    private Spinner projectsSpinner;
    private Spinner categorySpinner;

    @Inject Router router;

    public static Fragment newInstance(){
        AddTransactionFragment fragment = new AddTransactionFragment();
        return fragment;
    }

    @InjectPresenter
    AddTransactionFragmentPresenter addTransactionFragmentPresenter;

    @ProvidePresenter
    public AddTransactionFragmentPresenter provideAddTransactionFragmentPresenter(){
        return new AddTransactionFragmentPresenter(DataBaseManager.getInstance(getActivity().getApplicationContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangeFragmentTitleListener){
            listener = (ChangeFragmentTitleListener)context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        return inflater.inflate(R.layout.fragment_add_transaction, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goBackButton = getView().findViewById(R.id.back_from_add_transaction_fragment);
        addTransactionButton = getView().findViewById(R.id.add_transaction_button);
        projectsSpinner = getView().findViewById(R.id.project_type);
        categorySpinner = getView().findViewById(R.id.category_type);

        addTransactionFragmentPresenter.setProjectsType();
        addTransactionFragmentPresenter.setCategoryType();

        goBackButton.setOnClickListener(v -> addTransactionFragmentPresenter.goBackToTransactionFragment());
        addTransactionButton.setOnClickListener(v -> addTransactionFragmentPresenter.addTransaction());
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
    }

    @Override
    public void showTransactionFragment() {
        router.replaceScreen(new Screens.TransactionFragmentScreen());
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
