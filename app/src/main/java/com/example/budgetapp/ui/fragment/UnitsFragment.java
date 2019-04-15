package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.CategoriesFragmentPresenter;
import com.example.budgetapp.mvp.presenter.UnitsFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.CategoriesFragmentView;
import com.example.budgetapp.mvp.view.fragment.UnitsFragmentView;
import com.example.budgetapp.ui.adapter.list.CategoriesListAdapter;
import com.example.budgetapp.ui.adapter.list.UnitsListAdapter;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UnitsFragment extends BaseFragment implements UnitsFragmentView {

    private UnitsListAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_fab) FloatingActionButton fab;

    @InjectPresenter
    UnitsFragmentPresenter presenter;

    @ProvidePresenter
    UnitsFragmentPresenter providePresenter() {
        UnitsFragmentPresenter presenter = new UnitsFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static UnitsFragment newInstance(){
        return new UnitsFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new UnitsListAdapter(presenter.getUnitsListPresenter());
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> presenter.fabAction());
        updateUI();
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_units;
    }

    @Override
    int getTitleRes() {
        return R.string.units_fragment;
    }

    public void updateUI() {
        presenter.loadData();
    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
