package com.example.budgetapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.budgetapp.App;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.CategoryFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.CategoryFragmentView;
import com.example.budgetapp.ui.adapter.list.TransactionsListAdapter;
import com.example.budgetapp.utils.Constants;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CategoryFragment extends BaseFragment implements CategoryFragmentView {

    private static final String ARG_CATEGORY_ID = "category_id";
    private int categoryId;
    private TransactionsListAdapter adapter;

    @BindView(R.id.category_name_text)
    TextView categoryNameText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @InjectPresenter
    CategoryFragmentPresenter presenter;

    @ProvidePresenter
    public CategoryFragmentPresenter providePresenter() {
        CategoryFragmentPresenter presenter = new CategoryFragmentPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    public static Fragment newInstance(int categoryId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        CategoryFragment fragment = new CategoryFragment();
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
        inflater.inflate(R.menu.entity_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_entity:
                presenter.editCategory();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new TransactionsListAdapter(presenter.getTransactionsListPresenter());
        recyclerView.setAdapter(adapter);
        updateUI();
    }

    @Override
    int getLayoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    int getTitleRes() {
        return R.string.category_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.DELETE_REQUEST:
                    if(data.getIntExtra(DeleteEntityDialogFragment.TAG_RESULT_DELETE, -1) == Constants.RESULT_OK){
                        presenter.deleteCategory();
                        break;
                    }
                default:
                    break;
            }
        }
    }

    public void updateUI() {
        presenter.loadData(categoryId);
    }

    @Override
    public void updateTransactionsList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setCategoryName(String categoryName) {
        categoryNameText.setText(categoryName);
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
