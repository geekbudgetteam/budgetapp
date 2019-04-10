package com.example.budgetapp.di;

import com.example.budgetapp.di.modules.AppModule;
import com.example.budgetapp.di.modules.CiceroneModule;
import com.example.budgetapp.di.modules.DatabaseModule;
import com.example.budgetapp.mvp.presenter.AddCategoryFragmentPresenter;
import com.example.budgetapp.mvp.presenter.AddProjectElementPresenter;
import com.example.budgetapp.mvp.presenter.AddProjectFragmentPresenter;
import com.example.budgetapp.mvp.presenter.AddTransactionFragmentPresenter;
import com.example.budgetapp.mvp.presenter.AddUnitFragmentPresenter;
import com.example.budgetapp.mvp.presenter.ProjectFragmentPresenter;
import com.example.budgetapp.mvp.presenter.ProjectsHolderPresenter;
import com.example.budgetapp.mvp.presenter.ProjectsPresenter;
import com.example.budgetapp.mvp.presenter.TransactionFragmentPresenter;
import com.example.budgetapp.mvp.presenter.TransactionsFragmentPresenter;
import com.example.budgetapp.mvp.presenter.UpdateProjectFragmentPresenter;
import com.example.budgetapp.mvp.presenter.UpdateTransactionFragmentPresenter;
import com.example.budgetapp.ui.activity.MainActivity;
import com.example.budgetapp.ui.fragment.AddTransactionFragment;
import com.example.budgetapp.ui.fragment.ProjectsHolderFragment;
import com.example.budgetapp.ui.fragment.UpdateProjectFragment;

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

    void inject(UpdateProjectFragment updateProjectFragment);

    void inject(TransactionsFragmentPresenter presenter);
    void inject(TransactionFragmentPresenter presenter);
    void inject(AddTransactionFragmentPresenter presenter);
    void inject(UpdateTransactionFragmentPresenter presenter);

    void inject(ProjectsPresenter presenter);

    void inject(AddProjectElementPresenter presenter);

    void inject(ProjectsHolderPresenter presenter);
    void inject(AddTransactionFragment fragment);


    void inject(AddProjectFragmentPresenter presenter);

    void inject(UpdateProjectFragmentPresenter presenter);
    void inject(ProjectFragmentPresenter presenter);

    void inject(AddCategoryFragmentPresenter presenter);
    void inject(AddUnitFragmentPresenter presenter);


}
