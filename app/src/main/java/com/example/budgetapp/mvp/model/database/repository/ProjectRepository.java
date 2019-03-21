package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.ProjectDao;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

public class ProjectRepository implements ProjectStorage {

    private ProjectDao projectDao;

    @Inject
    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Completable addProject(Project project) {
        return projectDao.insertProject(project);
    }

    @Override
    public Maybe<Project> getProject(int id) {
        return projectDao.getProject(id);
    }

    @Override
    public Flowable<List<Project>> getProjectsList() {
        return projectDao.getProjectsList();
    }

    @Override
    public Completable updateProject(Project project) {
        return projectDao.insertProject(project);
    }

    @Override
    public Completable deleteProject(Project project) {
        return projectDao.deleteProject(project);
    }
}
