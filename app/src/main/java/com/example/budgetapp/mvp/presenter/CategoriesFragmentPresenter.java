package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.view.fragment.CategoriesFragmentView;
import com.example.budgetapp.mvp.view.row.EntityRowView;
import com.example.budgetapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class CategoriesFragmentPresenter extends MvpPresenter<CategoriesFragmentView> {

    private Scheduler scheduler;
    private Disposable disposable;
    private EntityListPresenter<Category> categoriesListPresenter = new CategoriesFragmentPresenter.CategoriesListPresenter();
    private List<Category> categories = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    CategoryStorage categoryStorage;

    public CategoriesFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadData(){
        disposable = categoryStorage.getCategoriesList()
                .observeOn(scheduler)
                .subscribe(categories -> {
                    CategoriesFragmentPresenter.this.categories = categories;
                    CategoriesFragmentPresenter.this.getViewState().updateData();
                });
    }

    public EntityListPresenter<Category> getCategoriesListPresenter() {
        return categoriesListPresenter;
    }

    public void fabAction(){
        router.navigateTo(new Screens.AddCategoryFragmentScreen());
    }

    public void onBack() {
        router.exit();
    }

    class CategoriesListPresenter implements EntityListPresenter<Category> {

        @Override
        public void bindEntityListRow(int pos, EntityRowView<Category> rowView) {
            if (categories.size() != 0) {
                rowView.setEntity(categories.get(pos));
            }
        }

        @Override
        public int getEntitiesCount() {
            return categories.size();
        }

        @Override
        public void navigateToEntity(Category category) {
            router.navigateTo(new Screens.CategoryFragmentScreen(category.getId()));
        }
    }
}
