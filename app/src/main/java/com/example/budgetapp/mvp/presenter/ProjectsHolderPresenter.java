package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.view.ProjectsHolderView;
import com.example.budgetapp.navigation.Screens;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ProjectsHolderPresenter extends MvpPresenter<ProjectsHolderView> {

    @Inject
    Router router;

    public void fabAction() {
        router.navigateTo(new Screens.AddProjectFragmentScreen());
    }

    public void onBack() {
        router.exit();
    }
}
