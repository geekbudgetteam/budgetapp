package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.ProjectElementDao;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.storage.ProjectElementStorage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class ProjectElementRepository implements ProjectElementStorage {

    private ProjectElementDao projectElementDao;

    @Inject
    public ProjectElementRepository(ProjectElementDao projectElementDao) {
        this.projectElementDao = projectElementDao;
    }

    @Override
    public void addProjectElement(ProjectElement projectElement) {
        projectElementDao.insertProjectElement(projectElement);
    }

    @Override
    public Maybe<ProjectElement> getProjectElement(int id) {
        return projectElementDao.getProjectElement(id);
    }

    @Override
    public Flowable<List<ProjectElement>> getProjectElementsList() {
        return projectElementDao.getProjectElementsList();
    }

    @Override
    public void updateProjectElement(ProjectElement projectElement) {
        projectElementDao.insertProjectElement(projectElement);
    }

    @Override
    public void deleteProjectElement(ProjectElement projectElement) {
        projectElementDao.deleteProjectElement(projectElement);
    }
}
