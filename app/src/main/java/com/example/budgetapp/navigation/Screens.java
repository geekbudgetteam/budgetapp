package com.example.budgetapp.navigation;

import android.support.v4.app.Fragment;

import com.example.budgetapp.ui.fragment.AddProjectFragment;
import com.example.budgetapp.ui.fragment.DeveloperFragment;
import com.example.budgetapp.ui.fragment.FamilyBudgetFragment;
import com.example.budgetapp.ui.fragment.FeedbackFragment;
import com.example.budgetapp.ui.fragment.ProjectElementFragment;
import com.example.budgetapp.ui.fragment.ProjectsFragment;
import com.example.budgetapp.ui.fragment.TransactionFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class TransactionFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return TransactionFragment.newInstance();
        }
    }

    public static class ProjectsFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return ProjectsFragment.newInstance();
        }
    }

    public static class DeveloperFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return DeveloperFragment.newInstance();
        }
    }

    public static class FeedbackFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return FeedbackFragment.newInstance();
        }
    }

    public static class AddProjectFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return AddProjectFragment.newInstance();
        }
    }

    public static class FamilyBudgetPresenterScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return FamilyBudgetFragment.newInstance();
        }
    }

    public static class ProjectElementFragmentScreen extends SupportAppScreen{
        private int projectId;

        public ProjectElementFragmentScreen(int projectId){
            this.projectId = projectId;
        }
        @Override
        public Fragment getFragment() {
            return ProjectElementFragment.newInstance(projectId);
        }
    }
}
