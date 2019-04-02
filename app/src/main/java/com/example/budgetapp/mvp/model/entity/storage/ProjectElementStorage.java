package com.example.budgetapp.mvp.model.entity.storage;

import com.example.budgetapp.mvp.model.entity.ProjectElement;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface ProjectElementStorage {

    void addProjectElement(ProjectElement projectElement);

    Maybe<ProjectElement> getProjectElement(int id);

    Flowable<List<ProjectElement>> getProjectElementsList();

    void updateProjectElement(ProjectElement projectElement);

    void deleteProjectElement(ProjectElement projectElement);
}
