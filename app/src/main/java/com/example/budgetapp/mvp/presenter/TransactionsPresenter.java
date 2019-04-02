package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;
import com.example.budgetapp.mvp.view.TransactionFragmentView;
import com.example.budgetapp.mvp.view.TransactionRowView;
import com.example.budgetapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TransactionsPresenter extends MvpPresenter<TransactionFragmentView> {

    private Scheduler scheduler;
    private Disposable disposable;
    private ITransactionsListPresenter transactionsListPresenter = new TransactionsPresenter.TransactionsListPresenter();
    private List<TransactionDetail> transactions = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    TransactionStorage transactionStorage;

    public TransactionsPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadTransactions(){
        final int[] totalAmount = new int[1];
        disposable = transactionStorage.getTransactionDetailsList()
                .subscribeOn(Schedulers.io())
                .map(new Function<List<TransactionDetail>, List<TransactionDetail>>() {
                    @Override
                    public List<TransactionDetail> apply(List<TransactionDetail> transactions) throws Exception {
                        for (TransactionDetail transaction: transactions) {
                            totalAmount[0] += transaction.getAmount();
                        }
                        return transactions;
                    }
                })
                .observeOn(scheduler)
                .subscribe(new Consumer<List<TransactionDetail>>() {
                    @Override
                    public void accept(List<TransactionDetail> transactions) throws Exception {
                        TransactionsPresenter.this.transactions = transactions;
                        TransactionsPresenter.this.getViewState().updateTransactionsList();
                        TransactionsPresenter.this.getViewState().updateTotalAmount(totalAmount[0]);
                    }
                });
    }

    public ITransactionsListPresenter getTransactionsListPresenter() {
        return transactionsListPresenter;
    }

    public void fabAction(){
        router.navigateTo(new Screens.AddTransactionFragmentScreen());
    }

    public void onBack() {
        router.exit();
    }

    class TransactionsListPresenter implements ITransactionsListPresenter {

        @Override
        public void bindTransactionsListRow(int pos, TransactionRowView rowView) {
            if (transactions != null) {
                rowView.setTransaction(transactions.get(pos));
            }
        }

        @Override
        public int getTransactionsCount() {
            return transactions.size();
        }

        @Override
        public void navigateToTransaction(TransactionDetail transaction) {
            router.navigateTo(new Screens.FamilyBudgetPresenterScreen());
        }
    }
}
