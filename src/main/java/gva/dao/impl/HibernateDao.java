package gva.dao.impl;

import gva.dao.Dao;
import gva.exception.DaoException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        Transaction transaction = getSession().beginTransaction();
        try {
            getSession().save(object);
            transaction.commit();
        } catch (HibernateException exception) {
            transaction.rollback();
            throw new DaoException(exception);
        }
    }

    @Override
    public void update(T object) throws DaoException {
        Transaction transaction = getSession().beginTransaction();
        try {
            getSession().update(object);
            transaction.commit();
        } catch (HibernateException exception) {
            transaction.rollback();
            throw new DaoException(exception);
        }
    }

    @Override
    public void delete(T object) throws DaoException {
        Transaction transaction = getSession().beginTransaction();
        try {
            getSession().delete(object);
            transaction.commit();
        } catch (HibernateException exception) {
            transaction.rollback();
            throw new DaoException(exception);
        }
    }
}
