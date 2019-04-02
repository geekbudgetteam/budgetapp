package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.view.AddTransactionView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class AddTransactionPresenter extends MvpPresenter<AddTransactionView> {

    private Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ProjectStorage projectStorage;
    @Inject
    CategoryStorage categoryStorage;
    @Inject
    TransactionStorage transactionStorage;
    private CompositeDisposable disposables = new CompositeDisposable();
    private IProjectsSpinnerPresenter projectsSpinnerPresenter = new ProjectsSpinnerPresenter();
    private ICategoriesSpinnerPresenter categoriesSpinnerPresenter = new CategoriesSpinnerPresenter();
    private List<Project> projects = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public AddTransactionPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    public void loadData() {
        disposables.add(projectStorage.getProjectsList()
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe((projects) -> {
                    this.projects = projects;
                    disposables.add(categoryStorage.getCategoriesList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(scheduler)
                            .subscribe((categories) -> this.categories = categories));
                    getViewState().updateData();
                }));
    }

    public IProjectsSpinnerPresenter getProjectsSpinnerPresenter() {
        return projectsSpinnerPresenter;
    }

    public ICategoriesSpinnerPresenter getCategoriesSpinnerPresenter() {
        return categoriesSpinnerPresenter;
    }

    public void showAddProjectFragment() {
        router.navigateTo(new Screens.AddProjectFragmentScreen());
    }

    public void showAddCategoryFragment() {
        router.navigateTo(new Screens.AddCategoryFragmentScreen());
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void addTransaction() {
        getViewState().getData();
    }

    public void addTransaction(Transaction transaction) {
        transactionStorage.addTransaction(transaction);
        onBack();
    }

    public void onBack() {
        router.exit();
    }

    class ProjectsSpinnerPresenter implements IProjectsSpinnerPresenter {

        @Override
        public int getProjectsCount() {
            return projects.size();
        }

        @Override
        public Project getProject(int position) {
            return projects.get(position);
        }

        @Override
        public List<Project> getProjectsList() {
            return projects;
        }
    }

    class CategoriesSpinnerPresenter implements ICategoriesSpinnerPresenter {

        @Override
        public int getCategoriesCount() {
            return categories.size();
        }

        @Override
        public Category getCategory(int position) {
            return categories.get(position);
        }

        @Override
        public List<Category> getCategoriesList() {
            return categories;
        }
    }
}
