package com.example.budgetapp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.budgetapp.ui.fragment.ProjectsListFragment;
import com.example.budgetapp.utils.Constants;

public class ProjectsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment mCurrentFragment;

    public ProjectsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProjectsListFragment.newInstance(Constants.EXPENSE_PROJECTS);
            case 1:
                return ProjectsListFragment.newInstance(Constants.INCOME_PROJECTS);
            default:
                return ProjectsListFragment.newInstance(Constants.EXPENSE_PROJECTS);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Constants.EXPENSE_TITLE;
            case 1:
                return Constants.INCOME_TITLE;
            default:
                return "Illegal title";
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
