package gva.dao;

import gva.exception.DaoException;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public interface Dao<T> {
    void create(T object) throws DaoException;

    List<T> findAll() throws DaoException;

    T findById(Long id) throws DaoException;

    void update(T object) throws DaoException;

    void delete(T object) throws DaoException;

    void deleteAll() throws DaoException;
}
