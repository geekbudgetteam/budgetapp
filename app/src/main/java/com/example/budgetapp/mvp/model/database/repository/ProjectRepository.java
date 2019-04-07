package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.ProjectDao;
import com.example.budgetapp.mvp.model.entity.Project;
import com.example.budgetapp.mvp.model.entity.storage.ProjectStorage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class ProjectRepository implements ProjectStorage {

    private ProjectDao projectDao;

    @Inject
    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Completable addProject(Project project) {
        return Completable.fromAction(() -> projectDao.insertProject(project))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Project> getProject(int id) {
        return projectDao.getProject(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<Project>> getProjectsList() {
        return projectDao.getProjectsList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateProject(Project project) {
        return Completable.fromAction(() -> projectDao.insertProject(project))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteProject(Project project) {
        return Completable.fromAction(() -> projectDao.deleteProject(project))
                .subscribeOn(Schedulers.io());
    }
}
