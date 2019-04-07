package com.example.budgetapp.navigation;

import android.support.v4.app.Fragment;

import com.example.budgetapp.ui.fragment.AddCategoryFragment;
import com.example.budgetapp.ui.fragment.AddProjectFragment;
import com.example.budgetapp.ui.fragment.AddTransactionFragment;
import com.example.budgetapp.ui.fragment.DeveloperFragment;
import com.example.budgetapp.ui.fragment.FeedbackFragment;
import com.example.budgetapp.ui.fragment.ProjectElementFragment;
import com.example.budgetapp.ui.fragment.ProjectFragment;
import com.example.budgetapp.ui.fragment.ProjectsHolderFragment;
import com.example.budgetapp.ui.fragment.TransactionsFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class TransactionsFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return TransactionsFragment.newInstance();
        }
    }

    public static class AddTransactionFragmentScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return AddTransactionFragment.newInstance();
        }
    }

    public static class ProjectsFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return ProjectsHolderFragment.newInstance();
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

    public static class AddCategoryFragmentScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return AddCategoryFragment.newInstance();
        }
    }

    public static class ProjectFragmentScreen extends SupportAppScreen {
        private int projectId;

        public ProjectFragmentScreen(int projectId) {
            this.projectId = projectId;
        }

        @Override
        public Fragment getFragment() {
            return ProjectFragment.newInstance(projectId);
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
