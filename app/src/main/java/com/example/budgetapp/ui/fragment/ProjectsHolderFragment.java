package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.ProjectsHolderPresenter;
import com.example.budgetapp.mvp.view.fragment.ProjectsHolderFragmentView;
import com.example.budgetapp.ui.adapter.ProjectsFragmentPagerAdapter;

import butterknife.BindView;

public class ProjectsHolderFragment extends BaseFragment implements ProjectsHolderFragmentView {

    private ProjectsFragmentPagerAdapter adapter;

    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.fragment_fab) FloatingActionButton fab;
    @BindView(R.id.tabs)TabLayout tabLayout;

    @InjectPresenter
    ProjectsHolderPresenter presenter;

    public static ProjectsHolderFragment newInstance() {
        return new ProjectsHolderFragment();
    }

    @ProvidePresenter
    ProjectsHolderPresenter providePresenter() {
        ProjectsHolderPresenter presenter = new ProjectsHolderPresenter();
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ProjectsFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        tabLayout.setupWithViewPager(viewPager);
        fab.setOnClickListener(v -> presenter.fabAction());
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_projects;
    }

    @Override
    int getTitleRes() {
        return R.string.projects_fragment;
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
