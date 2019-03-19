package com.example.budgetapp.ui.fragment;

import android.content.Context;
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
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;
import com.example.budgetapp.ui.activity.MainActivity;

public class AddProjectFragment extends MvpAppCompatFragment implements AddProjectFragmentView {

    private final int title = R.string.add_project_fragment;
    private ChangeFragmentTitleListener listener;

    public static Fragment newInstance(){
        AddProjectFragment fragment = new AddProjectFragment();
        return fragment;
    }

    @InjectPresenter
    AddProjectFragmentPresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangeFragmentTitleListener){
            listener = (ChangeFragmentTitleListener)context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addproject,null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
    }
}
