package com.example.budgetapp.di;

import com.example.budgetapp.di.modules.AppModule;
import com.example.budgetapp.di.modules.CiceronModule;
import com.example.budgetapp.ui.activity.MainActivity;
import com.example.budgetapp.ui.fragment.AddTransactionFragment;
import com.example.budgetapp.ui.fragment.FamilyBudgetFragment;
import com.example.budgetapp.ui.fragment.ProjectsFragment;
import com.example.budgetapp.ui.fragment.TransactionFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules ={
        AppModule.class,
        CiceronModule.class
})

public interface AppComponent {
    void inject (MainActivity mainActivity);
    void inject (ProjectsFragment projectsFragment);
    void inject (FamilyBudgetFragment familyBudgetFragment);

    void inject(TransactionFragment fragment);

    void inject(AddTransactionFragment fragment);
}
