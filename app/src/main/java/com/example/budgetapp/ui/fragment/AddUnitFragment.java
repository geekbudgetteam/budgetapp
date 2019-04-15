package com.example.budgetapp.ui.fragment;

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
import com.example.budgetapp.mvp.model.entity.Unit;
import com.example.budgetapp.mvp.presenter.AddUnitFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.AddUnitFragmentView;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddUnitFragment extends BaseFragment implements AddUnitFragmentView {

    @BindView(R.id.name_input)
    EditText nameInput;

    @InjectPresenter
    AddUnitFragmentPresenter presenter;

    @ProvidePresenter
    AddUnitFragmentPresenter providePresenter() {
        AddUnitFragmentPresenter presenter = new AddUnitFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance() {
        AddUnitFragment fragment = new AddUnitFragment();
        return fragment;
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
                presenter.addUnit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_add_unit;
    }

    @Override
    int getTitleRes() {
        return R.string.add_unit_fragment;
    }

    @Override
    public void getData() {
        String name = nameInput.getText().toString();
        if (name.equals("")) {
            presenter.addDataError(Objects.requireNonNull(getContext()).getResources().getString(R.string.project_element_name));
            return;
        }
//        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        presenter.addUnit(new Unit(name));
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
