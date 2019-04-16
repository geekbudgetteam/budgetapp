package com.example.budgetapp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.budgetapp.ui.common.BackButtonListener;
import com.example.budgetapp.ui.common.ChangeFragmentTitleListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
        listener.setToolbarTitle(title);
    }

    abstract int getLayoutRes();

    abstract int getTitleRes();

    protected void hideKeyboard(){
        try {
            InputMethodManager input = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (input != null) {
                input.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
