package com.example.budgetapp.di;

import com.example.budgetapp.di.modules.AppModule;
import com.example.budgetapp.di.modules.CiceroneModule;
import com.example.budgetapp.di.modules.DatabaseModule;
import com.example.budgetapp.mvp.presenter.AddTransactionPresenter;
import com.example.budgetapp.mvp.presenter.ProjectElementPresenter;
import com.example.budgetapp.mvp.presenter.ProjectsHolderPresenter;
import com.example.budgetapp.mvp.presenter.ProjectsPresenter;
import com.example.budgetapp.mvp.presenter.TransactionsPresenter;
import com.example.budgetapp.ui.activity.MainActivity;
import com.example.budgetapp.ui.fragment.AddTransactionFragment;
import com.example.budgetapp.ui.fragment.FamilyBudgetFragment;
import com.example.budgetapp.ui.fragment.ProjectsHolderFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        CiceroneModule.class,
        DatabaseModule.class
})

public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(ProjectsHolderFragment projectsHolderFragment);
    void inject(FamilyBudgetFragment familyBudgetFragment);
    void inject(TransactionsPresenter presenter);
    void inject(ProjectsPresenter presenter);

    void inject(ProjectElementPresenter presenter);

    void inject(ProjectsHolderPresenter presenter);
    void inject(AddTransactionFragment fragment);
    void inject(AddTransactionPresenter presenter);
}
