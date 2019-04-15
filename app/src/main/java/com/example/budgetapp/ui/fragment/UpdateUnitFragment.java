package com.example.budgetapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.presenter.UpdateCategoryFragmentPresenter;
import com.example.budgetapp.mvp.presenter.UpdateUnitFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.UpdateUnitFragmentView;
import com.example.budgetapp.utils.Constants;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UpdateUnitFragment extends BaseFragment implements UpdateUnitFragmentView {

    private static final String ARG_UNIT_ID = "unit_id";
    private int unitId;

    @BindView(R.id.unit_name_input)
    EditText unitNameInput;

    @InjectPresenter
    UpdateUnitFragmentPresenter presenter;

    @ProvidePresenter
    public UpdateUnitFragmentPresenter providePresenter() {
        UpdateUnitFragmentPresenter presenter = new UpdateUnitFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance(int unitId) {
        Bundle args = new Bundle();
        args.putInt(ARG_UNIT_ID, unitId);
        UpdateUnitFragment fragment = new UpdateUnitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unitId = getArguments().getInt(ARG_UNIT_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_and_delete_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_entity:
                presenter.updateUnit();
                return true;
            case R.id.delete_entity:
                DeleteEntityDialogFragment fragment = new DeleteEntityDialogFragment();
                fragment.setTargetFragment(this, Constants.DELETE_REQUEST);
                if (getFragmentManager() != null) {
                    fragment.show(getFragmentManager(), fragment.getClass().getName());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadData(unitId);
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_update_unit;
    }

    @Override
    int getTitleRes() {
        return R.string.unit_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.DELETE_REQUEST:
                    if(data.getIntExtra(DeleteEntityDialogFragment.TAG_RESULT_DELETE, -1) == Constants.RESULT_OK){
                        presenter.deleteUnit();
                        break;
                    }
                default:
                    break;
            }
        }
    }

    @Override
    public void setUnitName(String unitName) {
        unitNameInput.setText(unitName);
    }

    @Override
    public void getData() {
        String unitName = unitNameInput.getText().toString();
        if (unitName.equals("")) {
            presenter.addDataError(Objects.requireNonNull(getContext()).getResources().getString(R.string.category_name));
            return;
        }
        unitName = Character.toUpperCase(unitName.charAt(0)) + unitName.substring(1);
        Unit unit = new Unit(unitName);
        presenter.updateUnit(unit);
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
