package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.storage.CategoryStorage;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;
import com.example.budgetapp.mvp.view.fragment.CategoryFragmentView;
import com.example.budgetapp.mvp.view.row.EntityRowView;
import com.example.budgetapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class CategoryFragmentPresenter extends MvpPresenter<CategoryFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private EntityListPresenter<TransactionDetail> transactionsListPresenter = new TransactionsListPresenter();
    private Category category;
    private List<TransactionDetail> transactions = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    CategoryStorage categoryStorage;
    @Inject
    TransactionStorage transactionStorage;

    public CategoryFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int categoryId) {
        disposables.add(categoryStorage.getCategory(categoryId)
                .observeOn(scheduler)
                .subscribe(category -> {
                    CategoryFragmentPresenter.this.category = category;
                    setCategoriesFieldsSelection();
                    disposables.add(transactionStorage.getTransactionDetailsListByCategory(categoryId)
                            .observeOn(scheduler)
                            .subscribe(transactionDetails -> {
                                CategoryFragmentPresenter.this.transactions = transactionDetails;
                                getViewState().updateTransactionsList();
                            }));
                }));
    }

    private void setCategoriesFieldsSelection() {
        getViewState().setCategoryName(category.getName());
    }

    public EntityListPresenter<TransactionDetail> getTransactionsListPresenter() {
        return transactionsListPresenter;
    }

    public void editCategory(){
        router.navigateTo(new Screens.UpdateCategoryFragmentScreen(category.getId()));
    }

    public void deleteCategory(){
        disposables.add(categoryStorage.deleteCategory(category)
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }

    class TransactionsListPresenter implements EntityListPresenter<TransactionDetail> {

        @Override
        public void bindEntityListRow(int pos, EntityRowView<TransactionDetail> rowView) {
            if (transactions.size() != 0){
                rowView.setEntity(transactions.get(pos));
            }
        }

        @Override
        public int getEntitiesCount() {
            return transactions.size();
        }

        @Override
        public void navigateToEntity(TransactionDetail entity) {
            router.navigateTo(new Screens.TransactionFragmentScreen(entity.getId()));
        }
    }
}
