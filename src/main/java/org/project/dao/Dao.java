package org.project.dao;

import org.project.entity.Entity;
import org.project.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class Dao<T, E extends Entity> {
    public abstract List<E> findAll(int offset, int total) throws DaoException;
    public abstract Optional<E> findById(T key) throws DaoException;
    public abstract boolean insert(E entity) throws DaoException;
    public abstract boolean update(E entity) throws DaoException;
}
