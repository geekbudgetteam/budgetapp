package com.example.budgetapp.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.view.ProjectElementView;
import com.example.budgetapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ProjectElementPresenter extends MvpPresenter<ProjectElementView> {

    private static final String ADD_CATEGORY_ITEM = "Добавить категорию";
    private static final String ADD_UNIT_ITEM = "Добавить ед. измерения";

    private Scheduler scheduler;

    private ICategoriesSpinnerPresenter categoriesSpinnerPresenter = new CategoriesSpinnerPresenter();
    private IUnitsSpinnerPresenter unitsSpinnerPresenter = new UnitsSpinnerPresenter();

    private List<Category> categories = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();

    public ProjectElementPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @SuppressLint("CheckResult")
    public void loadData() {
        dbManager.getCategoriesList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.take(3).delay(1, TimeUnit.SECONDS);
                    }
                })
                .observeOn(scheduler)
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        ProjectElementPresenter.this.categories = categories;
                        ProjectElementPresenter.this.categories.add(0, new Category(Constants.SPINNER_CHOICE));
                        ProjectElementPresenter.this.categories.add(new Category(Constants.SPINNER_ADD));

                        dbManager.getUnitsList()
                                .subscribeOn(Schedulers.io())
                                .retryWhen(throwableObservable -> throwableObservable.take(3).delay(1, TimeUnit.SECONDS))
                                .observeOn(scheduler)
                                .subscribe(units -> {
                                    ProjectElementPresenter.this.units = units;
                                    ProjectElementPresenter.this.units.add(0, new Unit(Constants.SPINNER_CHOICE));
                                    ProjectElementPresenter.this.units.add(new Unit(Constants.SPINNER_ADD));
                                    ProjectElementPresenter.this.getViewState().updateData();
                                });
                    }
                });
    }

    public void checkCategorySpinnerChoice(int position) {
        System.out.println("checkCategorySpinnerChoice");
        if (position == categories.size() - 1) {
            getViewState().showMessage("Переход к экрану добавления категории");
        }

    }

    public void checkUnitSpinnerChoice(int position) {
        if (position == units.size() - 1) {
            getViewState().showMessage("Переход к экрану добавления единицы измерения");
        }
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
        dbManager.addProjectElement(element);
        getViewState().actionComplete();

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
            return units.get(position);
        }

        @Override
        public List<Unit> getUnitsList() {
            return units;
        }
    }
}
