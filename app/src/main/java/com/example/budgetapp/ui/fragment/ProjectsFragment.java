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
import com.example.budgetapp.ui.activity.MainActivity;
import com.example.budgetapp.ui.adapter.ProjectsFragmentPagerAdapter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

public class ProjectsFragment extends Fragment {

    private final int title = R.string.projects_fragment;

    private ProjectsFragmentPagerAdapter adapter;
    private ViewPager viewPager;
    private FloatingActionButton floatingActionButton;
    @Inject Router router;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setToolbarTitle(title);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        return inflater.inflate(R.layout.fragment_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatingActionButton = view.findViewById(R.id.fragment_fab);
        viewPager = view.findViewById(R.id.view_pager);
        adapter = new ProjectsFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setFABClickListener();
    }

    public void setFABClickListener(){
        floatingActionButton.setOnClickListener(v ->
                router.navigateTo(new Screens.AddProjectFragmentScreen()));
    }
}
