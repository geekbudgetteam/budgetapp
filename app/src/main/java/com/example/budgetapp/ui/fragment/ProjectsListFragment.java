package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.ProjectsPresenter;
import com.example.budgetapp.mvp.view.ProjectsListView;
import com.example.budgetapp.ui.adapter.ProjectsListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProjectsListFragment extends MvpAppCompatFragment implements ProjectsListView {

    private static final String ARG_FRAGMENT_TYPE = "fragment_type";

    private int fragmentType;
    private ProjectsListAdapter adapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @InjectPresenter
    ProjectsPresenter presenter;

    @ProvidePresenter
    ProjectsPresenter providePresenter() {
        ProjectsPresenter presenter = new ProjectsPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static ProjectsListFragment newInstance(int fragmentType) {
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_TYPE, fragmentType);
        ProjectsListFragment fragment = new ProjectsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentType = getArguments().getInt(ARG_FRAGMENT_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProjectsListAdapter(presenter.getProjectsListPresenter());
        recyclerView.setAdapter(adapter);
        updateUI();
    }

    public void updateUI() {
        presenter.loadProjects(fragmentType);
    }

    @Override
    public void updateProjectsList() {
        adapter.notifyDataSetChanged();
    }

    public int getFragmentType() {
        return fragmentType;
    }

    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
