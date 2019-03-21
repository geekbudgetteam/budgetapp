package com.example.budgetapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.ui.activity.ChangeFragmentTitleListener;
import com.example.budgetapp.ui.adapter.ProjectsFragmentPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.terrakok.cicerone.Router;

public class ProjectsFragment extends Fragment {

    private final int title = R.string.projects_fragment;
    private ChangeFragmentTitleListener listener;

    private ProjectsFragmentPagerAdapter adapter;

    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.fragment_fab) FloatingActionButton fab;
    @BindView(R.id.tabs)TabLayout tabLayout;

    @Inject Router router;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

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
        App.getInstance().getAppComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ProjectsFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        tabLayout.setupWithViewPager(viewPager);
        setFABClickListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.setToolbarTitle(title);
    }

    public void setFABClickListener(){
        fab.setOnClickListener(v ->
                router.navigateTo(new Screens.AddProjectFragmentScreen()));
    }
}
