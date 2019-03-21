package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.view.AddTransactionView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class AddTransactionPresenter extends MvpPresenter<AddTransactionView> {

    private Scheduler scheduler;
    private List<Project> projectsList;
    private List<Category> categoriesList;
    private ArrayList<String> projectsName;
    private ArrayList<String> categoryName;

    public AddTransactionPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        projectsName = new ArrayList<>();
        categoryName = new ArrayList<>();
    }

    public void goBackToTransactionFragment() {
        getViewState().showTransactionFragment();
    }

    public void addTransaction() {
        getViewState().showTransactionFragment();
    }

    public void setProjectsType() {
        Observer<List<Project>> observer = new Observer<List<Project>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<Project> s) {
                projectsList = s;
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                if (projectsList == null) {
                    ArrayList<String> projectsListName = new ArrayList<>();
                    projectsListName.add("Пусто");
                    getViewState().setProjectsList(projectsListName);
                } else {
                    for (int i = 0; i < projectsList.size(); i++) {
                        projectsName.add(projectsList.get(i).getName());
                    }
                    getViewState().setProjectsList(projectsName);
                }

            }
        };
        dataBaseManager.getProjectsList().subscribe(observer);
    }

    public void setCategoryType(){
        Observer<List<Category>> observer = new Observer<List<Category>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<Category> s) {
                categoriesList = s;
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                if (categoriesList == null) {
                    ArrayList<String> categoriesListName = new ArrayList<>();
                    categoriesListName.add("Пусто");
                    getViewState().setCategoryList(categoriesListName);
                } else {
                    for (int i = 0; i < categoriesList.size(); i++) {
                        categoryName.add(categoriesList.get(i).getName());
                    }
                    getViewState().setCategoryList(categoryName);
                }

            }
        };
        dataBaseManager.getCategoriesList().subscribe(observer);
    }
}
