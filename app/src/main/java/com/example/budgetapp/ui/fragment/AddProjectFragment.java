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
import com.example.budgetapp.mvp.presenter.AddProjectFragmentPresenter;
import com.example.budgetapp.mvp.view.AddProjectFragmentView;

public class AddProjectFragment extends MvpAppCompatFragment implements AddProjectFragmentView {

    public static Fragment newInstance(){
        AddProjectFragment fragment = new AddProjectFragment();
        return fragment;
    }

    @InjectPresenter
    AddProjectFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addproject,null);
        return view;
    }
}
