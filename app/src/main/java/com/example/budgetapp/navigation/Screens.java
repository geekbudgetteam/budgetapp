package com.example.budgetapp.navigation;

import android.support.v4.app.Fragment;

import com.example.budgetapp.ui.fragment.AddCategoryFragment;
import com.example.budgetapp.ui.fragment.AddProjectElementFragment;
import com.example.budgetapp.ui.fragment.AddProjectFragment;
import com.example.budgetapp.ui.fragment.AddTransactionFragment;
import com.example.budgetapp.ui.fragment.AddUnitFragment;
import com.example.budgetapp.ui.fragment.CategoriesFragment;
import com.example.budgetapp.ui.fragment.CategoryFragment;
import com.example.budgetapp.ui.fragment.FeedbackFragment;
import com.example.budgetapp.ui.fragment.ProjectFragment;
import com.example.budgetapp.ui.fragment.ProjectsHolderFragment;
import com.example.budgetapp.ui.fragment.TransactionFragment;
import com.example.budgetapp.ui.fragment.TransactionsFragment;
import com.example.budgetapp.ui.fragment.UpdateCategoryFragment;
import com.example.budgetapp.ui.fragment.UpdateProjectFragment;
import com.example.budgetapp.ui.fragment.UpdateTransactionFragment;

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

    public static class TransactionFragmentScreen extends SupportAppScreen {
        private int transactionId;

        public TransactionFragmentScreen(int transactionId) {
            this.transactionId = transactionId;
        }

        @Override
        public Fragment getFragment() {
            return TransactionFragment.newInstance(transactionId);
        }
    }

    public static class UpdateTransactionFragmentScreen extends SupportAppScreen {
        private int transactionId;

        public UpdateTransactionFragmentScreen(int transactionId) {
            this.transactionId = transactionId;
        }

        @Override
        public Fragment getFragment() {
            return UpdateTransactionFragment.newInstance(transactionId);
        }
    }

    public static class ProjectsFragmentScreen extends SupportAppScreen{
        @Override
        public Fragment getFragment() {
            return ProjectsHolderFragment.newInstance();
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

    public static class CategoriesFragmentScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return CategoriesFragment.newInstance();
        }
    }

    public static class CategoryFragmentScreen extends SupportAppScreen {
        private int categoryId;

        public CategoryFragmentScreen(int categoryId) {
            this.categoryId = categoryId;
        }

        @Override
        public Fragment getFragment() {
            return CategoryFragment.newInstance(categoryId);
        }
    }

    public static class AddCategoryFragmentScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return AddCategoryFragment.newInstance();
        }
    }

    public static class UpdateCategoryFragmentScreen extends SupportAppScreen {
        private int categoryId;

        public UpdateCategoryFragmentScreen(int categoryId) {
            this.categoryId = categoryId;
        }

        @Override
        public Fragment getFragment() {
            return UpdateCategoryFragment.newInstance(categoryId);
        }
    }

    public static class AddUnitFragmentScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return AddUnitFragment.newInstance();
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

    public static class UpdateProjectFragmentScreen extends SupportAppScreen {
        private int projectId;

        public UpdateProjectFragmentScreen(int projectId) {
            this.projectId = projectId;
        }

        @Override
        public Fragment getFragment() {
            return UpdateProjectFragment.newInstance(projectId);
        }
    }

    public static class AddProjectElementFragmentScreen extends SupportAppScreen{
        private int projectId;

        public AddProjectElementFragmentScreen(int projectId){
            this.projectId = projectId;
        }
        @Override
        public Fragment getFragment() {
            return AddProjectElementFragment.newInstance(projectId);
        }
    }
}
