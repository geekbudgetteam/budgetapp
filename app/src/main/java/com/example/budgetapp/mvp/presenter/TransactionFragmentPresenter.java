package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;
import com.example.budgetapp.mvp.view.fragment.TransactionFragmentView;
import com.example.budgetapp.navigation.Screens;
import com.example.budgetapp.utils.Constants;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TransactionFragmentPresenter extends MvpPresenter<TransactionFragmentView> {

    private Scheduler scheduler;
    private CompositeDisposable disposables = new CompositeDisposable();
    private TransactionDetail transaction;

    @Inject
    Router router;
    @Inject
    TransactionStorage transactionStorage;

    public TransactionFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void loadData(int transactionId) {
        disposables.add(transactionStorage.getTransactionDetail(transactionId)
                .observeOn(scheduler)
                .subscribe(transaction -> {
                    TransactionFragmentPresenter.this.transaction = transaction;
                    setTransactionFieldsSelection();
                }));
    }

    private void setTransactionFieldsSelection() {
        String transactionType = null;
        if (transaction.getAmount() < 0){
            transactionType = Constants.EXPENSE_STRING;
        } else {
            transactionType = Constants.INCOME_STRING;
        }
        getViewState().setTransactionType(transactionType);
        getViewState().setProjectName(transaction.getProjectName());
        getViewState().setCategoryName(transaction.getCategoryName());
        String amount = String.format(Locale.getDefault(), "%.2f %s",
                Math.abs(transaction.getAmount()), Constants.CURRENCY);
        getViewState().setTransactionAmount(amount);
        String date = Constants.DATE_FORMAT.format(transaction.getDate());
        getViewState().setTransactionDate(date);
    }

    public void editTransaction(){
        router.navigateTo(new Screens.UpdateTransactionFragmentScreen(transaction.getId()));
    }

    public void deleteTransaction(){
        disposables.add(transactionStorage.deleteTransaction(transaction.getId())
                .observeOn(scheduler)
                .subscribe(this::onBack));
    }

    public void onBack() {
        router.exit();
    }
}
