package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.FamilyBudgetPresenter;
import com.example.budgetapp.mvp.view.FamilyBudgetView;
import com.example.budgetapp.navigation.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class FamilyBudgetFragment extends MvpAppCompatFragment implements FamilyBudgetView {

    public static Fragment newInstance(){
        FamilyBudgetFragment fragment = new FamilyBudgetFragment();
        return fragment;
    }

    @Inject Router router;
    @InjectPresenter FamilyBudgetPresenter familyBudgetPresenter;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_family_budget,null);
        floatingActionButton = view.findViewById(R.id.fab_family_budget);
        setFABOnClickListener();
        return view;
    }

    public void setFABOnClickListener (){
        floatingActionButton.setOnClickListener(v -> {
            router.navigateTo(new Screens.ProjectElementFragmentScreen(1)); //TODO projectID передана заглушка
        });
    }
}
