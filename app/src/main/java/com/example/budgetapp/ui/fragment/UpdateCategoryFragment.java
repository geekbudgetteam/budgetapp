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
import com.example.budgetapp.mvp.model.entity.Category;
import com.example.budgetapp.mvp.presenter.UpdateCategoryFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.UpdateCategoryFragmentView;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UpdateCategoryFragment extends BaseFragment implements UpdateCategoryFragmentView {

    private static final String ARG_CATEGORY_ID = "category_id";
    private int categoryId;

    @BindView(R.id.category_name_input)
    EditText categoryNameInput;

    @InjectPresenter
    UpdateCategoryFragmentPresenter presenter;

    @ProvidePresenter
    public UpdateCategoryFragmentPresenter providePresenter() {
        UpdateCategoryFragmentPresenter presenter = new UpdateCategoryFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance(int categoryId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        UpdateCategoryFragment fragment = new UpdateCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
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
                presenter.updateCategory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadData(categoryId);
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_update_category;
    }

    @Override
    int getTitleRes() {
        return R.string.category_fragment;
    }

    @Override
    public void setCategoryName(String categoryName) {
        categoryNameInput.setText(categoryName);
    }

    @Override
    public void getData() {
        String categoryName = categoryNameInput.getText().toString();
        if (categoryName.equals("")) {
            presenter.addDataError(Objects.requireNonNull(getContext()).getResources().getString(R.string.category_name));
            return;
        }
        categoryName = Character.toUpperCase(categoryName.charAt(0)) + categoryName.substring(1);
        Category category = new Category(categoryName);
        presenter.updateCategory(category);
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
