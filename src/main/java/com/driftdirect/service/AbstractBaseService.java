package com.driftdirect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Paul on 11/6/2015.
 */
public abstract class AbstractBaseService<R extends JpaRepository, E> {
    protected  R repository;

    @Autowired
    public AbstractBaseService(R repository){
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    public E save(E entity) {
        return (E) repository.save(entity);
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return repository.findAll();
    }

    @SuppressWarnings("unchecked")
    public E findById(Long id) {
        return (E) repository.findOne(id);
    }

    @SuppressWarnings("unchecked")
    public void delete(Long id) {
        repository.delete(id);
    }
}
