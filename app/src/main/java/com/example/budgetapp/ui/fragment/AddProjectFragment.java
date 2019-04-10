package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.presenter.AddProjectFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.AddProjectFragmentView;
import com.example.budgetapp.utils.Constants;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddProjectFragment extends BaseFragment implements AddProjectFragmentView {

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

    @InjectPresenter
    AddProjectFragmentPresenter presenter;

    @ProvidePresenter
    public AddProjectFragmentPresenter providePresenter() {
        AddProjectFragmentPresenter presenter = new AddProjectFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance() {
        return new AddProjectFragment();
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
                presenter.addProject();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setProjectPeriodSpinnerListener();
        projectStartDateTitle.setVisibility(View.INVISIBLE);
        projectStartDateInput.setVisibility(View.INVISIBLE);
        projectFinishDateTitle.setVisibility(View.INVISIBLE);
        projectFinishDateInput.setVisibility(View.INVISIBLE);
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
    int getLayoutRes() {
        return R.layout.fragment_add_project;
    }

    @Override
    int getTitleRes() {
        return R.string.add_project_fragment;
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

        presenter.addProject(new Project(projectType, projectName, variable, projectPeriod, startPeriod, finishPeriod));
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
