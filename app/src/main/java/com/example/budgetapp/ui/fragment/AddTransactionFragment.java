package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Transaction;
import com.example.budgetapp.mvp.presenter.AddTransactionFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.AddTransactionFragmentView;
import com.example.budgetapp.ui.adapter.spinner.CategoriesSpinnerAdapter;
import com.example.budgetapp.ui.adapter.spinner.ProjectsSpinnerAdapter;
import com.example.budgetapp.ui.common.AmountInputFocusListener;

import java.util.Date;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddTransactionFragment extends BaseFragment implements AddTransactionFragmentView {

    private ProjectsSpinnerAdapter projectsSpinnerAdapter;
    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;

    @BindView(R.id.type_spinner) Spinner typeSpinner;
    @BindView(R.id.project_spinner) Spinner projectSpinner;
    @BindView(R.id.add_project_btn)
    ImageButton addProjectBtn;
    @BindView(R.id.category_spinner) Spinner categorySpinner;
    @BindView(R.id.add_category_btn)
    ImageButton addCategoryBtn;
    @BindView(R.id.amount_input) EditText amountInput;

    @InjectPresenter
    AddTransactionFragmentPresenter presenter;

    @ProvidePresenter
    public AddTransactionFragmentPresenter provideAddTransactionFragmentPresenter() {
        AddTransactionFragmentPresenter presenter = new AddTransactionFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static AddTransactionFragment newInstance(){
        return new AddTransactionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_entity:
                presenter.addTransaction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeSpinner.getAdapter();
        projectsSpinnerAdapter = new ProjectsSpinnerAdapter(this.getContext(), presenter.getProjectsSpinnerPresenter());
        projectSpinner.setAdapter(projectsSpinnerAdapter);
        addProjectBtn.setOnClickListener((v) -> presenter.showAddProjectFragment());
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this.getContext(), presenter.getCategoriesSpinnerPresenter());
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
        addCategoryBtn.setOnClickListener((v) -> presenter.showAddCategoryFragment());
        amountInput.setOnFocusChangeListener(new AmountInputFocusListener());
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
        int projectId = (int) projectsSpinnerAdapter.getItemId(projectSpinner.getSelectedItemPosition());
        if (projectId == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.transaction_project));
            return;
        }
        int categoryId = (int) categoriesSpinnerAdapter.getItemId(categorySpinner.getSelectedItemPosition());
        if (categoryId == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.transaction_category));
            return;
        }
        long date = new Date().getTime();
        if (amountInput.getText().length() == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.transaction_amount));
            return;
        }
        String amountValue = amountInput.getText().toString();
        String[] amountValues = amountValue.split(" ");
        float amount = Float.parseFloat(amountValues[0]);
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
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
