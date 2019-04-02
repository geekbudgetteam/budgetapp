package com.example.budgetapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;
import com.example.budgetapp.ui.common.BackButtonListener;

import butterknife.ButterKnife;

public abstract class BaseFragment extends MvpAppCompatFragment implements BackButtonListener {

    private int title;
    private ChangeFragmentTitleListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangeFragmentTitleListener) {
            listener = (ChangeFragmentTitleListener) context;
        }
        title = getTitleRes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
    }

    abstract int getLayoutRes();

    abstract int getTitleRes();
}
