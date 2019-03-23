package com.boioio.restsincluj.dao;

import com.boioio.restsincluj.domain.AbstractModel;

import java.util.Collection;

public interface BaseDAO<T extends AbstractModel> {

    Collection<T> getAll();

    T findById(Long id);

    T update(T model);

    boolean delete(T model);
}
