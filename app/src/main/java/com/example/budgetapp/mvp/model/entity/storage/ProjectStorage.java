package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Project;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface ProjectStorage {

    Completable addProject(Project project);

    Maybe<Project> getProject(int id);

    Flowable<List<Project>> getProjectsList();

    Completable updateProject(Project project);

    Completable deleteProject(Project project);
}
