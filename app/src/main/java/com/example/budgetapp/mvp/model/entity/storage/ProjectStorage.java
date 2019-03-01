package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.Project;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectStorage {

    Observable<Boolean> addProject(Project project);

    Observable<Project> getProject(int id);

    Observable<List<Project>> getProjectsList();

    Observable<Boolean> updateProject(Project project);

    Observable<Boolean> deleteProject(Project project);
}
