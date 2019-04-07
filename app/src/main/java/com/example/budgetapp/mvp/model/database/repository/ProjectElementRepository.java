package com.example.budgetapp.mvp.model.database.repository;

import com.example.budgetapp.mvp.model.database.dao.ProjectElementDao;
import com.example.budgetapp.mvp.model.entity.ProjectElement;
import com.example.budgetapp.mvp.model.entity.storage.ProjectElementStorage;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class ProjectElementRepository implements ProjectElementStorage {

    private ProjectElementDao projectElementDao;

    @Inject
    public ProjectElementRepository(ProjectElementDao projectElementDao) {
        this.projectElementDao = projectElementDao;
    }

    @Override
    public Completable addProjectElement(ProjectElement projectElement) {
        return Completable.fromAction(() -> projectElementDao.insertProjectElement(projectElement))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<ProjectElement> getProjectElement(int id) {
        return projectElementDao.getProjectElement(id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<ProjectElement>> getProjectElementsList() {
        return projectElementDao.getProjectElementsList()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateProjectElement(ProjectElement projectElement) {
        return Completable.fromAction(() -> projectElementDao.insertProjectElement(projectElement))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteProjectElement(ProjectElement projectElement) {
        return Completable.fromAction(() -> projectElementDao.deleteProjectElement(projectElement))
                .subscribeOn(Schedulers.io());
    }
}
