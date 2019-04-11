package com.example.budgetapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.budgetapp.R;
import com.example.budgetapp.mvp.presenter.DeleteEntityDialogFragmentPresenter;
import com.example.budgetapp.mvp.view.fragment.DeleteEntityDialogFragmentView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteEntityDialogFragment extends MvpAppCompatDialogFragment implements DeleteEntityDialogFragmentView {

    public static final String TAG_RESULT_DELETE = "delete_result";

    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    @BindView(R.id.delete_btn)
    Button deleteBtn;

    @InjectPresenter
    DeleteEntityDialogFragmentPresenter presenter;

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancelBtn.setOnClickListener(v -> presenter.cancel());
        deleteBtn.setOnClickListener(v -> presenter.deleteEntity());
    }

    @Override
    public void sendResult(int result) {
        Intent intent = new Intent();
        intent.putExtra(TAG_RESULT_DELETE, result);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
        dismiss();
    }



}
