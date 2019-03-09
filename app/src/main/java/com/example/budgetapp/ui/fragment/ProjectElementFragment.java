package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.database.DataBaseManager;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.presenter.ProjectElementPresenter;
import com.example.budgetapp.mvp.view.ProjectElementView;
import com.example.budgetapp.ui.adapter.CategoriesSpinnerAdapter;
import com.example.budgetapp.ui.adapter.UnitsSpinnerAdapter;
import com.example.budgetapp.utils.Constants;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProjectElementFragment extends MvpAppCompatFragment implements ProjectElementView {

    private static final String ARG_PROJECT_ID = "project_id";

    @InjectPresenter
    ProjectElementPresenter presenter;

    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;
    private boolean categoriesSpinnerInitialized;
    private UnitsSpinnerAdapter unitsSpinnerAdapter;
    private boolean unitsSpinnerInitialized;

    private int projectId;

    private EditText nameInput;
    private Spinner categorySpinner;
    private Spinner unitSpinner;
    private EditText quantityInput;
    private EditText amountInput;
    private CheckBox monitoringCheckbox;
    private TextView minimumQuantityTitle;
    private EditText minimumQuantityInput;
    private Button addBtn;


    public static ProjectElementFragment newInstance(int projectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        ProjectElementFragment fragment = new ProjectElementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    ProjectElementPresenter providePresenter() {
        ProjectElementPresenter presenter = new ProjectElementPresenter(AndroidSchedulers.mainThread(), DataBaseManager.getInstance(getActivity().getApplicationContext()));
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectId = getArguments().getInt(ARG_PROJECT_ID);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_project_element, container, false);
        initViews(view);
        updateUI();
        return view;
    }

    private void initViews(View view) {
        nameInput = view.findViewById(R.id.name_input);
        categorySpinner = view.findViewById(R.id.category_spinner);
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this.getContext(), presenter.getCategoriesSpinnerPresenter());
        categorySpinner.setAdapter(categoriesSpinnerAdapter);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onItemSelected");
                System.out.println("CategoriesSpinnerInitialized " + categoriesSpinnerInitialized);
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


        unitSpinner = view.findViewById(R.id.unit_spinner);
        unitsSpinnerAdapter = new UnitsSpinnerAdapter(this.getContext(), presenter.getUnitsSpinnerPresenter());
        unitSpinner.setAdapter(unitsSpinnerAdapter);
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
        quantityInput = view.findViewById(R.id.quantity_input);
        amountInput = view.findViewById(R.id.amount_input);
        monitoringCheckbox = view.findViewById(R.id.monitoring_checkbox);
        monitoringCheckbox.setChecked(false);
        monitoringCheckbox.setOnClickListener((v -> presenter.monitoredCheckboxClicked(monitoringCheckbox.isChecked())));
        minimumQuantityTitle = view.findViewById(R.id.minimum_quantity_title);
        minimumQuantityTitle.setVisibility(View.INVISIBLE);
        minimumQuantityInput = view.findViewById(R.id.minimum_quantity_input);
        minimumQuantityInput.setVisibility(View.INVISIBLE);
        addBtn = view.findViewById(R.id.add_element_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addElement();
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
    public void getData() {
        String name = nameInput.getText().toString();
        if (name == null) {
            presenter.addDataError("Наименование");
        }
        Project project = new Project(projectId);
        Category category = categoriesSpinnerAdapter.getItem(categorySpinner.getSelectedItemPosition());
        Unit unit = unitsSpinnerAdapter.getItem(unitSpinner.getSelectedItemPosition());
        float quantity = Float.parseFloat(quantityInput.getText().toString());
        float amount = Float.parseFloat(amountInput.getText().toString());
        int monitored = monitoringCheckbox.isChecked() ? Constants.MONITORED : Constants.UNMONITORED;
        float minimalQuantity = monitored == Constants.MONITORED ? Float.parseFloat(minimumQuantityInput.getText().toString()) : 0f;
        presenter.addElement(new ProjectElement(name, project, category, unit, quantity, amount, monitored, minimalQuantity));
    }


    @Override
    public void setMinimumQuantityVisible(boolean visible) {
        minimumQuantityInput.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        minimumQuantityTitle.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getView().getContext(),
                message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void actionComplete() {
        onBackPressed();
    }

    public boolean onBackPressed() {
        getActivity().onBackPressed();
        return true;
    }
}
