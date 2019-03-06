package com.example.budgetapp.di;

import com.example.budgetapp.di.modules.AppModule;
import com.example.budgetapp.di.modules.CiceronModule;
import com.example.budgetapp.mvp.presenter.MainFragmentPresenter;
import com.example.budgetapp.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules ={
        AppModule.class,
        CiceronModule.class})

public interface AppComponent {
    void inject (MainActivity mainActivity);
    void inject (MainFragmentPresenter mainFragmentPresenter);
}
