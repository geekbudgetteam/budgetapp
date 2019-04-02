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
import com.example.budgetapp.mvp.presenter.AddCategoryFragmentPresenter;
import com.example.budgetapp.mvp.view.AddCategoryFragmentView;
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;

public class AddCategoryFragment extends MvpAppCompatFragment implements AddCategoryFragmentView {

    private final int title = R.string.add_category_fragment;
    @InjectPresenter
    AddCategoryFragmentPresenter presenter;
    private ChangeFragmentTitleListener listener;

    public static Fragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangeFragmentTitleListener) {
            listener = (ChangeFragmentTitleListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
    }
}
