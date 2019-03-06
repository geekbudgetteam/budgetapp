package com.example.budgetapp.di.modules;

import com.example.budgetapp.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides
    public App app(){
        return app;
    }
}
