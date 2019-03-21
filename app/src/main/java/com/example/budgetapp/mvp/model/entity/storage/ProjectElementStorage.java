package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.ProjectElement;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface ProjectElementStorage {

    Completable addProjectElement(ProjectElement projectElement);

    Maybe<ProjectElement> getProjectElement(int id);

    Flowable<List<ProjectElement>> getProjectElementsList();

    Completable updateProjectElement(ProjectElement projectElement);

    Completable deleteProjectElement(ProjectElement projectElement);
}
