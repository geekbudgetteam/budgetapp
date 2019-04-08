package com.example.budgetapp.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.ProjectElementStorage;
import com.example.budgetapp.mvp.model.entity.storage.UnitStorage;
import com.example.budgetapp.mvp.view.fragment.AddProjectElementFragmentView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.utils.Constants;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class AddProjectElementPresenter extends MvpPresenter<AddProjectElementFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private ICategoriesSpinnerPresenter categoriesSpinnerPresenter = new CategoriesSpinnerPresenter();
    private IUnitsSpinnerPresenter unitsSpinnerPresenter = new UnitsSpinnerPresenter();
    private List<Category> categories = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    CategoryStorage categoryStorage;
    @Inject
    UnitStorage unitStorage;
    @Inject
    ProjectElementStorage projectElementStorage;

    public AddProjectElementPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @SuppressLint("CheckResult")
    public void loadData() {
        disposables.add(categoryStorage.getCategoriesList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new Function<Flowable<Throwable>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.take(3).delay(1, TimeUnit.SECONDS);
                    }
                })
                .observeOn(scheduler)
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        AddProjectElementPresenter.this.categories = categories;
                        disposables.add(unitStorage.getUnitsList()
                                .subscribeOn(Schedulers.io())
                                .retryWhen(throwableObservable -> throwableObservable.take(3).delay(1, TimeUnit.SECONDS))
                                .observeOn(scheduler)
                                .subscribe(units -> {
                                    AddProjectElementPresenter.this.units = units;
                                    AddProjectElementPresenter.this.getViewState().updateData();
                                }));
                    }}));
    }

    public void addDataError(String field) {
        getViewState().showMessage(Constants.ERROR_MESSAGE + field);
    }

    public void monitoredCheckboxClicked(boolean monitored) {
        getViewState().setMinimumQuantityVisible(monitored);
    }

    public void addElement() {
        getViewState().getData();
    }

    public void addElement(ProjectElement element) {
        disposables.add(projectElementStorage.addProjectElement(element)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void showAddCategoryFragment() {
        router.navigateTo(new Screens.AddCategoryFragmentScreen());
    }

    public void showAddUnitFragment() {
        router.navigateTo(new Screens.AddUnitFragmentScreen());
    }

    public void onBack() {
        router.exit();
    }

    public ICategoriesSpinnerPresenter getCategoriesSpinnerPresenter() {
        return categoriesSpinnerPresenter;
    }

    public IUnitsSpinnerPresenter getUnitsSpinnerPresenter() {
        return unitsSpinnerPresenter;
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

    class UnitsSpinnerPresenter implements IUnitsSpinnerPresenter {

        @Override
        public int getUnitsCount() {
            return units.size();
        }

        @Override
        public Unit getUnit(int position) {
            Unit unit = null;
            if (position != -1) {
                unit = units.get(position);
            }
            return unit;
        }

        @Override
        public List<Unit> getUnitsList() {
            return units;
        }
    }
}
