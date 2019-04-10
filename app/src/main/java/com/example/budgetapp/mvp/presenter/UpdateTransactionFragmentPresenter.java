package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.view.fragment.UpdateTransactionFragmentView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class UpdateTransactionFragmentPresenter extends MvpPresenter<UpdateTransactionFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Transaction transaction;
    private IProjectsSpinnerPresenter projectsSpinnerPresenter = new ProjectsSpinnerPresenter();
    private ICategoriesSpinnerPresenter categoriesSpinnerPresenter = new CategoriesSpinnerPresenter();
    private List<Project> projects = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    TransactionStorage transactionStorage;
    @Inject
    ProjectStorage projectStorage;
    @Inject
    CategoryStorage categoryStorage;

    public UpdateTransactionFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int transactionId) {
        disposables.add(transactionStorage.getTransaction(transactionId)
                .observeOn(scheduler)
                .subscribe(transaction -> {
                    UpdateTransactionFragmentPresenter.this.transaction = transaction;
                    disposables.add(projectStorage.getProjectsList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(scheduler)
                            .subscribe((projects) -> {
                                this.projects = projects;
                                disposables.add(categoryStorage.getCategoriesList()
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(scheduler)
                                        .subscribe((categories) -> {
                                            this.categories = categories;
                                            getViewState().updateData();
                                            setTransactionFieldsSelection();
                                        }));
                            }));
                }));
    }

    private void setTransactionFieldsSelection(){
        int transactionType = 0;
        if (transaction.getAmount() < 0){
            transactionType = Constants.EXPENSE;
        } else {
            transactionType = Constants.INCOME;
        }
        getViewState().setTransactionTypeSpinnerSelection(transactionType);
        getViewState().setProjectSpinnerSelection(transaction.getProjectId() - 1); // -1 is alignment db and list indexes
        getViewState().setCategorySpinnerSelection(transaction.getCategoryId() - 1); // -1 is alignment db and list indexes
        String amount = String.format(Locale.getDefault(), "%.2f %s",
                Math.abs(transaction.getAmount()), Constants.CURRENCY);
        getViewState().setTransactionAmountSelection(amount);
        String date = Constants.DATE_FORMAT.format(transaction.getDate());
        getViewState().setTransactionDateSelection(date);
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

    public void updateTransaction() {
        getViewState().getData();
    }

    public void updateTransaction(Transaction transaction) {
        transaction.setId(this.transaction.getId());
        disposables.add(transactionStorage.addTransaction(transaction)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }

    public IProjectsSpinnerPresenter getProjectsSpinnerPresenter() {
        return projectsSpinnerPresenter;
    }

    public ICategoriesSpinnerPresenter getCategoriesSpinnerPresenter() {
        return categoriesSpinnerPresenter;
    }

    class ProjectsSpinnerPresenter implements IProjectsSpinnerPresenter {

        @Override
        public int getProjectsCount() {
            return projects.size();
        }

        @Override
        public Project getProject(int position) {
            Project project = null;
            if (position != -1) {
                project = projects.get(position);
            }
            return project;
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
            Category category = null;
            if (position != -1) {
                category = categories.get(position);
            }
            return category;
        }

        @Override
        public List<Category> getCategoriesList() {
            return categories;
        }
    }
}
