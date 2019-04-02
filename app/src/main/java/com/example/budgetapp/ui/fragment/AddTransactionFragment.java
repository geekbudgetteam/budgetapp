package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.presenter.AddTransactionPresenter;
import com.example.budgetapp.mvp.view.AddTransactionView;
import com.example.budgetapp.ui.adapter.CategoriesSpinnerAdapter;
import com.example.budgetapp.ui.adapter.ProjectsSpinnerAdapter;

import java.util.Date;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddTransactionFragment extends BaseFragment implements AddTransactionView {

    @BindView(R.id.add_project_btn)
    ImageButton addProjectBtn;
    @BindView(R.id.add_category_btn)
    ImageButton addCategoryBtn;

    @BindView(R.id.type_spinner) Spinner typeSpinner;
    @BindView(R.id.project_spinner) Spinner projectSpinner;
    @BindView(R.id.add_transaction_btn)
    Button addTransactionBtn;
    @BindView(R.id.category_spinner) Spinner categorySpinner;
    private ProjectsSpinnerAdapter projectsSpinnerAdapter;
    @BindView(R.id.amount_input) EditText amountInput;
    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;

    @InjectPresenter
    AddTransactionPresenter presenter;

    @ProvidePresenter
    public AddTransactionPresenter provideAddTransactionFragmentPresenter(){
        AddTransactionPresenter presenter = new AddTransactionPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static AddTransactionFragment newInstance(){
        return new AddTransactionFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        projectsSpinnerAdapter = new ProjectsSpinnerAdapter(this.getContext(), presenter.getProjectsSpinnerPresenter());
        projectSpinner.setAdapter(projectsSpinnerAdapter);
        addProjectBtn.setOnClickListener((v) -> presenter.showAddProjectFragment());
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this.getContext(), presenter.getCategoriesSpinnerPresenter());
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
        addCategoryBtn.setOnClickListener((v) -> presenter.showAddCategoryFragment());
        addTransactionBtn.setOnClickListener(v -> presenter.addTransaction());
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_add_transaction;
    }

    @Override
    int getTitleRes() {
        return R.string.add_transaction_fragment;
    }

    @Override
    public void updateData() {
        projectsSpinnerAdapter.notifyDataSetChanged();
        categoriesSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void getData() {
        int projectId = projectsSpinnerAdapter.getItem(projectSpinner.getSelectedItemPosition()).getId();
        if (projectId == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.transaction_project));
            return;
        }
        int categoryId = categoriesSpinnerAdapter.getItem(categorySpinner.getSelectedItemPosition()).getId();
        if (categoryId == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.transaction_category));
            return;
        }
        long date = new Date().getTime();
        float amount = Float.parseFloat(amountInput.getText().toString());
        if (amount == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.transaction_amount));
            return;
        }
        if (typeSpinner.getSelectedItemPosition() == 0 && amount > 0) {
            amount *= -1;
        }


        Transaction transaction = new Transaction(projectId, categoryId, date, amount);

        presenter.addTransaction(transaction);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
