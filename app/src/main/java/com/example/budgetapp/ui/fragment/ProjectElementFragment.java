package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.presenter.ProjectElementPresenter;
import com.example.budgetapp.mvp.view.ProjectElementView;
import com.example.budgetapp.ui.adapter.CategoriesSpinnerAdapter;
import com.example.budgetapp.ui.adapter.UnitsSpinnerAdapter;
import com.example.budgetapp.utils.Constants;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProjectElementFragment extends BaseFragment implements ProjectElementView {

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
    @BindView(R.id.unit_spinner)
    Spinner unitSpinner;
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
    @BindView(R.id.add_element_btn)
    Button addBtn;

    @InjectPresenter
    ProjectElementPresenter presenter;

    @ProvidePresenter
    ProjectElementPresenter providePresenter() {
        ProjectElementPresenter presenter = new ProjectElementPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static ProjectElementFragment newInstance(int projectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        ProjectElementFragment fragment = new ProjectElementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectId = getArguments().getInt(ARG_PROJECT_ID);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this.getContext(), presenter.getCategoriesSpinnerPresenter());
        categorySpinner.setAdapter(categoriesSpinnerAdapter);
        unitsSpinnerAdapter = new UnitsSpinnerAdapter(this.getContext(), presenter.getUnitsSpinnerPresenter());
        unitSpinner.setAdapter(unitsSpinnerAdapter);
        monitoringCheckbox.setChecked(false);
        monitoringCheckbox.setOnClickListener((v -> presenter.monitoredCheckboxClicked(monitoringCheckbox.isChecked())));
        minimumQuantityTitle.setVisibility(View.INVISIBLE);
        minimumQuantityInput.setVisibility(View.INVISIBLE);
        addBtn.setOnClickListener(v -> presenter.addElement());
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

    private void initViews(View view) {

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (categoriesSpinnerInitialized) {
                    presenter.checkCategorySpinnerChoice(position);
                } else {
                    categoriesSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (unitsSpinnerInitialized) {
                    presenter.checkUnitSpinnerChoice(position);
                } else {
                    unitsSpinnerInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    }
}
