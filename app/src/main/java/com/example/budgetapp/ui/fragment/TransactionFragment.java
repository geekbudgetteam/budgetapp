package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.TransactionFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.TransactionFragmentView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransactionFragment extends BaseFragment implements TransactionFragmentView {

    private static final String ARG_TRANSACTION_ID = "transaction_id";
    private int transactionId;

    @BindView(R.id.transaction_type_text)
    TextView transactionTypeText;
    @BindView(R.id.project_name_text)
    TextView projectNameText;
    @BindView(R.id.category_name_text)
    TextView categoryNameText;
    @BindView(R.id.amount_text)
    TextView amountText;
    @BindView(R.id.transaction_date_text)
    TextView transactionDateText;

    @InjectPresenter
    TransactionFragmentPresenter presenter;

    @ProvidePresenter
    public TransactionFragmentPresenter providePresenter() {
        TransactionFragmentPresenter presenter = new TransactionFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance(int transactionId) {
        Bundle args = new Bundle();
        args.putInt(ARG_TRANSACTION_ID, transactionId);
        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transactionId = getArguments().getInt(ARG_TRANSACTION_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.entity_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_entity:
                presenter.editTransaction();
                return true;
            case R.id.delete_entity:
                presenter.deleteTransaction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadData(transactionId);
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_transaction;
    }

    @Override
    int getTitleRes() {
        return R.string.transaction_fragment;
    }

    @Override
    public void setTransactionType(String type) {
        transactionTypeText.setText(type);
    }

    @Override
    public void setProjectName(String projectName) {
        projectNameText.setText(projectName);
    }

    @Override
    public void setCategoryName(String categoryName) {
        categoryNameText.setText(categoryName);
    }

    @Override
    public void setTransactionAmount(String amount) {
        amountText.setText(amount);
    }

    @Override
    public void setTransactionDate(String date) {
        transactionDateText.setText(date);
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
