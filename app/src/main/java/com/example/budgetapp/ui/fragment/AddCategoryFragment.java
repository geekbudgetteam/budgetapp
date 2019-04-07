package com.example.budgetapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.presenter.AddCategoryFragmentPresenter;
import com.example.budgetapp.mvp.view.AddCategoryFragmentView;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddCategoryFragment extends BaseFragment implements AddCategoryFragmentView {

    @BindView(R.id.name_input)
    EditText nameInput;
    @BindView(R.id.add_category_btn)
    Button addBtn;

    @InjectPresenter
    AddCategoryFragmentPresenter presenter;

    @ProvidePresenter
    AddCategoryFragmentPresenter providePresenter() {
        AddCategoryFragmentPresenter presenter = new AddCategoryFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance() {
        AddCategoryFragment fragment = new AddCategoryFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(v -> presenter.addCategory());
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_add_category;
    }

    @Override
    int getTitleRes() {
        return R.string.add_category_fragment;
    }

    @Override
    public void getData() {
        String name = nameInput.getText().toString();
        if (name.equals("")) {
            presenter.addDataError(Objects.requireNonNull(getContext()).getResources().getString(R.string.project_element_name));
            return;
        }
        presenter.addCategory(new Category(name));

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
