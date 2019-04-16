package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.view.fragment.UpdateCategoryFragmentView;
import com.example.budgetapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class UpdateCategoryFragmentPresenter extends MvpPresenter<UpdateCategoryFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Category category;

    @Inject
    Router router;
    @Inject
    CategoryStorage categoryStorage;

    public UpdateCategoryFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int categoryId) {
        disposables.add(categoryStorage.getCategory(categoryId)
                .observeOn(scheduler)
                .subscribe(category -> {
                    UpdateCategoryFragmentPresenter.this.category = category;
                    setCategoryFieldsSelection();
                }));
    }

    private void setCategoryFieldsSelection() {
        getViewState().setCategoryName(category.getName());
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void updateCategory() {
        getViewState().getData();
    }

    public void updateCategory(Category category) {
        category.setId(this.category.getId());

        disposables.add(categoryStorage.updateCategory(category)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }
}
