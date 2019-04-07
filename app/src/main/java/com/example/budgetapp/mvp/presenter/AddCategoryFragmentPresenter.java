package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.view.AddCategoryFragmentView;
import com.example.budgetapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class AddCategoryFragmentPresenter extends MvpPresenter<AddCategoryFragmentView> {

    @Inject
    Router router;
    @Inject
    CategoryStorage categoryStorage;
    private Scheduler scheduler;
    private Disposable disposable;

    public AddCategoryFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void addCategory() {
        getViewState().getData();
    }

    public void addCategory(Category category) {
        disposable = categoryStorage.addCategory(category)
                .observeOn(scheduler)
                .subscribe(this::onBack);
    }

    public void onBack() {
        router.exit();
    }
}
