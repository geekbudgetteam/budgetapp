package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.ProjectElement;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectElementStorage {

    Observable<Boolean> addProjectElement(ProjectElement projectElement);

    Observable<ProjectElement> getProjectElement(int id);

    Observable<List<ProjectElement>> getProjectElementsList();

    Observable<Boolean> updateProjectElement(ProjectElement projectElement);

    Observable<Boolean> deleteProjectElement(ProjectElement projectElement);
}
