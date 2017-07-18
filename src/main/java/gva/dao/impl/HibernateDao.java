package gva.dao.impl;

import gva.dao.Dao;
import gva.exception.DaoException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

// TODO extract getById, getAll, deleteAll to this base class using reflection

@Transactional
public abstract class HibernateDao<T> implements Dao<T> {
    private SessionFactory sessionFactory;

    HibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(T object) throws DaoException {
        execute(Session::save, object);
    }

    @Override
    public void update(T object) throws DaoException {
        execute(Session::update, object);
    }

    @Override
    public void delete(T object) throws DaoException {
        execute(Session::delete, object);
    }

    private void execute(Executable<T> executable, T object) throws DaoException {
        try {
            executable.execute(getSession(), object);
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @FunctionalInterface
    private interface Executable<T> {
        void execute(Session session, T object);
    }
}
