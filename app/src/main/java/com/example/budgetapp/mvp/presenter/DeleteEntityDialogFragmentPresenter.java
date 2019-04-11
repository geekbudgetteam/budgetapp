package com.example.budgetapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.budgetapp.mvp.view.fragment.DeleteEntityDialogFragmentView;
import com.example.budgetapp.utils.Constants;

@InjectViewState
public class DeleteEntityDialogFragmentPresenter extends MvpPresenter<DeleteEntityDialogFragmentView> {

    public void cancel() {
        getViewState().sendResult(Constants.RESULT_CANCEL);
    }

    public void deleteEntity() {
        getViewState().sendResult(Constants.RESULT_OK);
    }
}
