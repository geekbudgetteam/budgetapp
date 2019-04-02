package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Project;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface ProjectStorage {

    void addProject(Project project);

    Maybe<Project> getProject(int id);

    Flowable<List<Project>> getProjectsList();

    void updateProject(Project project);

    void deleteProject(Project project);
}
