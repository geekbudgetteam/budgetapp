package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.DeveloperFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.DeveloperFragmentView;

public class DeveloperFragment extends MvpAppCompatFragment implements DeveloperFragmentView {

    public static Fragment newInstance(){
        DeveloperFragment fragment = new DeveloperFragment();
        return fragment;
    }

    @InjectPresenter
    DeveloperFragmentPresenter developerFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_developer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
