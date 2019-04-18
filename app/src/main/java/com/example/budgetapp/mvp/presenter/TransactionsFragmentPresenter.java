package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.model.entity.storage.TransactionStorage;
import com.example.budgetapp.mvp.model.entity.view.TransactionDetail;
import com.example.budgetapp.mvp.view.fragment.TransactionsFragmentView;
import com.example.budgetapp.mvp.view.row.EntityRowView;
import com.example.budgetapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TransactionsFragmentPresenter extends MvpPresenter<TransactionsFragmentView> {

    private Scheduler scheduler;
    private Disposable disposable;
    private EntityListPresenter<TransactionDetail> transactionsListPresenter = new TransactionsListPresenter();
    private List<TransactionDetail> transactions = new ArrayList<>();

    @Inject
    Router router;
    @Inject
    TransactionStorage transactionStorage;

    public TransactionsFragmentPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void loadData(){
        getViewState().showProgressBar(true);
        final int[] totalAmount = new int[1];
        disposable = transactionStorage.getTransactionDetailsList()
                .map(transactions -> {
                    for (TransactionDetail transaction: transactions) {
                        totalAmount[0] += transaction.getAmount();
                    }
                    return transactions;
                })
                .observeOn(scheduler)
                .subscribe(transactions -> {
                    TransactionsFragmentPresenter.this.transactions = transactions;
                    TransactionsFragmentPresenter.this.getViewState().showProgressBar(false);
                    TransactionsFragmentPresenter.this.getViewState().updateTransactionsList();
                    TransactionsFragmentPresenter.this.getViewState().updateTotalAmount(totalAmount[0]);
                });
    }

    public EntityListPresenter<TransactionDetail> getTransactionsListPresenter() {
        return transactionsListPresenter;
    }

    public void fabAction(){
        router.navigateTo(new Screens.AddTransactionFragmentScreen());
    }

    public void onBack() {
        router.exit();
    }

    class TransactionsListPresenter implements EntityListPresenter<TransactionDetail> {

        @Override
        public void bindEntityListRow(int pos, EntityRowView<TransactionDetail> rowView) {
            if (transactions != null){
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
