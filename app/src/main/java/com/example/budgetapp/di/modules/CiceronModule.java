package com.example.budgetapp.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

import javax.inject.Singleton;

@Module
public class CiceronModule {

    Cicerone<Router> cicerone = Cicerone.create();

    @Provides
    @Singleton
    public Cicerone<Router> cicerone(){
        return cicerone;
    }

    @Provides
    @Singleton
    public Router router(){
        return cicerone.getRouter();
    }

    @Provides
    @Singleton
    public NavigatorHolder navigatorHolder(){
        return cicerone.getNavigatorHolder();
    }
}
