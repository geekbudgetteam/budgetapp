package com.example.budgetapp.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class CiceroneModule {

    Cicerone<Router> cicerone = Cicerone.create();

    @Provides
    @Singleton
    public Cicerone<Router> cicerone(){
        return cicerone;
    }

    @Provides
    @Singleton
    public NavigatorHolder navigatorHolder(){
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    public Router router(){
        return cicerone.getRouter();
    }
}
