package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.presenter.UpdateProjectFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.UpdateProjectFragmentView;
import com.example.budgetapp.utils.Constants;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UpdateProjectFragment extends BaseFragment implements UpdateProjectFragmentView {

    private static final String ARG_PROJECT_ID = "project_id";
    private int projectId;

    @BindView(R.id.type_spinner)
    Spinner typeSpinner;
    @BindView(R.id.project_name_input)
    EditText projectNameInput;
    @BindView(R.id.project_duration_spinner)
    Spinner projectDurationSpinner;
    @BindView(R.id.project_period_spinner)
    Spinner projectPeriodSpinner;
    @BindView(R.id.project_start_date_title)
    TextView projectStartDateTitle;
    @BindView(R.id.project_start_date_input)
    EditText projectStartDateInput;
    @BindView(R.id.project_finish_date_title)
    TextView projectFinishDateTitle;
    @BindView(R.id.project_finish_date_input)
    EditText projectFinishDateInput;
    @BindView(R.id.update_project_btn)
    Button updateProjectBtn;

    @InjectPresenter
    UpdateProjectFragmentPresenter presenter;

    @ProvidePresenter
    public UpdateProjectFragmentPresenter providePresenter() {
        UpdateProjectFragmentPresenter presenter = new UpdateProjectFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance(int projectId) {
        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        UpdateProjectFragment fragment = new UpdateProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getInt(ARG_PROJECT_ID);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProjectPeriodSpinnerListener();
        presenter.loadData(projectId);
        updateProjectBtn.setOnClickListener(v -> presenter.updateProject());
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_update_project;
    }

    @Override
    int getTitleRes() {
        return R.string.project_fragment;
    }

    private void setProjectPeriodSpinnerListener() {
        projectPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.checkPeriodSpinnerChoice(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setProjectDateFieldsVisibility(boolean start, boolean finish) {
        if(!start){
            projectStartDateInput.setText(null);
        }
        projectStartDateTitle.setVisibility(start ? View.VISIBLE : View.INVISIBLE);
        projectStartDateInput.setVisibility(start ? View.VISIBLE : View.INVISIBLE);
        if (!finish){
            projectFinishDateInput.setText(null);
        }
        projectFinishDateTitle.setVisibility(finish ? View.VISIBLE : View.INVISIBLE);
        projectFinishDateInput.setVisibility(finish ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setProjectTypeSpinnerSelection(int type) {
        typeSpinner.setSelection(type);
    }

    @Override
    public void setProjectNameSelection(String projectNameSelection) {
        projectNameInput.setText(projectNameSelection);
    }

    @Override
    public void setProjectDurationSpinnerSelection(int durationSpinnerSelection) {
        projectDurationSpinner.setSelection(durationSpinnerSelection);
    }

    @Override
    public void setProjectPeriodSpinnerSelection(int periodSpinnerSelection) {
        projectPeriodSpinner.setSelection(periodSpinnerSelection);
    }

    @Override
    public void setProjectStartDateSelection(long startDateSelection) {
        if (startDateSelection != 0) {
            Date startDate = new Date(startDateSelection);
            String startDateText = Constants.DATE_FORMAT.format(startDate);
            projectStartDateInput.setText(startDateText);
        }
    }

    @Override
    public void setProjectFinishDateSelection(long finishDateSelection) {
        if (finishDateSelection != 0) {
            Date finishDate = new Date(finishDateSelection);
            String finishDateText = Constants.DATE_FORMAT.format(finishDate);
            projectStartDateInput.setText(finishDateText);
        }
    }

    @Override
    public void getData() {
        int projectType = typeSpinner.getSelectedItemPosition();
        String projectName = projectNameInput.getText().toString();
        if (projectName.equals("")) {
            presenter.addDataError(Objects.requireNonNull(getContext()).getResources().getString(R.string.project_name));
            return;
        }
        projectName = Character.toUpperCase(projectName.charAt(0)) + projectName.substring(1);
        int variable = projectDurationSpinner.getSelectedItemPosition();
        int projectPeriod = projectPeriodSpinner.getSelectedItemPosition();
        Date startDate = null;
        long startPeriod = 0;
        try {
            startDate = Constants.DATE_FORMAT.parse(projectStartDateInput.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startDate != null) {
            startPeriod = startDate.getTime();
            showMessage(startDate.toString() + " " + String.valueOf(startPeriod));
        }
        Date finishDate = null;
        long finishPeriod = 0;
        try {
            finishDate = Constants.DATE_FORMAT.parse(projectFinishDateInput.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (finishDate != null) {
            finishPeriod = finishDate.getTime();
            showMessage(finishDate.toString() + " " + String.valueOf(finishPeriod));
        }

        presenter.updateProject((new Project(projectType, projectName, variable, projectPeriod, startPeriod, finishPeriod)));
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
