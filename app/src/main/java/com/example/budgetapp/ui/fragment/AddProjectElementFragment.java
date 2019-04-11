package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.presenter.AddProjectElementPresenter;
import com.example.budgetapp.mvp.view.fragment.AddProjectElementFragmentView;
import com.example.budgetapp.ui.adapter.spinner.CategoriesSpinnerAdapter;
import com.example.budgetapp.ui.adapter.spinner.UnitsSpinnerAdapter;
import com.example.budgetapp.utils.Constants;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddProjectElementFragment extends BaseFragment implements AddProjectElementFragmentView {

    private static final String ARG_PROJECT_ID = "project_id";

    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;
    private boolean categoriesSpinnerInitialized;
    private UnitsSpinnerAdapter unitsSpinnerAdapter;
    private boolean unitsSpinnerInitialized;

    private int projectId;

    @BindView(R.id.name_input)
    EditText nameInput;
    @BindView(R.id.category_spinner)
    Spinner categorySpinner;
    @BindView(R.id.add_category_btn)
    ImageButton addCategoryBtn;
    @BindView(R.id.unit_spinner)
    Spinner unitSpinner;
    @BindView(R.id.add_unit_btn)
    ImageButton addUnitBtn;
    @BindView(R.id.quantity_input)
    EditText quantityInput;
    @BindView(R.id.amount_input)
    EditText amountInput;
    @BindView(R.id.monitoring_checkbox)
    CheckBox monitoringCheckbox;
    @BindView(R.id.minimum_quantity_title)
    TextView minimumQuantityTitle;
    @BindView(R.id.minimum_quantity_input)
    EditText minimumQuantityInput;

    @InjectPresenter
    AddProjectElementPresenter presenter;

    @ProvidePresenter
    AddProjectElementPresenter providePresenter() {
        AddProjectElementPresenter presenter = new AddProjectElementPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static AddProjectElementFragment newInstance(int projectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        AddProjectElementFragment fragment = new AddProjectElementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getInt(ARG_PROJECT_ID);
        }
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
                presenter.addElement();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this.getContext(), presenter.getCategoriesSpinnerPresenter());
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
        addCategoryBtn.setOnClickListener(v -> presenter.showAddCategoryFragment());
        unitsSpinnerAdapter = new UnitsSpinnerAdapter(this.getContext(), presenter.getUnitsSpinnerPresenter());
        unitSpinner.setAdapter(unitsSpinnerAdapter);
        addUnitBtn.setOnClickListener(v -> presenter.showAddUnitFragment());
        monitoringCheckbox.setChecked(false);
        monitoringCheckbox.setOnClickListener((v -> presenter.monitoredCheckboxClicked(monitoringCheckbox.isChecked())));
        minimumQuantityTitle.setVisibility(View.INVISIBLE);
        minimumQuantityInput.setVisibility(View.INVISIBLE);
        updateUI();
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_add_project_element;
    }

    @Override
    int getTitleRes() {
        return R.string.project_element_fragment;
    }

    public void updateUI() {
        presenter.loadData();
    }

    @Override
    public void updateData() {
        categoriesSpinnerAdapter.notifyDataSetChanged();
        unitsSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setMinimumQuantityVisible(boolean visible) {
        minimumQuantityInput.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        minimumQuantityTitle.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void getData() {
        String name = nameInput.getText().toString();
        if (name.equals("")) {
            presenter.addDataError(Objects.requireNonNull(getContext()).getResources().getString(R.string.project_element_name));
            return;
        }
        int categoryId = (int) categoriesSpinnerAdapter.getItemId(categorySpinner.getSelectedItemPosition());
        if (categoryId == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.project_element_category));
            return;
        }
        int unitId = (int) unitsSpinnerAdapter.getItemId(unitSpinner.getSelectedItemPosition());
        if (unitId == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.project_element_unit));
            return;
        }
        float quantity = Float.parseFloat(quantityInput.getText().toString());
        if (quantity == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.project_element_quantity));
            return;
        }
        float amount = Float.parseFloat(amountInput.getText().toString());
        if (amount == 0) {
            presenter.addDataError(getContext().getResources().getString(R.string.project_element_amount));
            return;
        }
        int monitored = monitoringCheckbox.isChecked() ? Constants.MONITORED : Constants.UNMONITORED;
        float minimalQuantity = monitored == Constants.MONITORED ? Float.parseFloat(minimumQuantityInput.getText().toString()) : 0f;
        presenter.addElement(new ProjectElement(name, projectId, categoryId, unitId, quantity, amount, monitored, minimalQuantity));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getView().getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        presenter.onBack();
    }
}
