package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.ProjectFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.ProjectFragmentView;
import com.example.budgetapp.ui.adapter.list.ProjectElementsListAdapter;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProjectFragment extends BaseFragment implements ProjectFragmentView {

    private static final String ARG_PROJECT_ID = "project_id";
    private int projectId;
    private ProjectElementsListAdapter adapter;

    @BindView(R.id.project_type_text)
    TextView projectTypeText;
    @BindView(R.id.project_name_text)
    TextView projectNameText;
    @BindView(R.id.project_duration_text)
    TextView projectDurationText;
    @BindView(R.id.project_period_text)
    TextView projectPeriodText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_fab)
    FloatingActionButton fab;

    @InjectPresenter
    ProjectFragmentPresenter presenter;

    @ProvidePresenter
    public ProjectFragmentPresenter providePresenter() {
        ProjectFragmentPresenter presenter = new ProjectFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance(int projectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getInt(ARG_PROJECT_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new ProjectElementsListAdapter(presenter.getProjectElementsListPresenter());
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(v -> presenter.fabAction());
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.entity_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_entity:
                presenter.editProject();
                return true;
            case R.id.delete_entity:
                presenter.deleteProject();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_project;
    }

    @Override
    int getTitleRes() {
        return R.string.project_fragment;
    }

    public void updateUI() {
        presenter.loadData(projectId);
    }

    @Override
    public void updateProjectElementsList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setProjectType(String type) {
        projectTypeText.setText(type);
    }

    @Override
    public void setProjectName(String projectName) {
        projectNameText.setText(projectName);
    }

    @Override
    public void setProjectDuration(String projectDuration) {
        projectDurationText.setText(projectDuration);
    }

    @Override
    public void setProjectPeriod(String projectPeriod) {
        projectPeriodText.setText(projectPeriod);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
