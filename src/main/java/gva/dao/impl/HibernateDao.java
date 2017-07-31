package gva.dao.impl;

import gva.dao.Dao;
import gva.exception.DaoException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiConsumer;

@Transactional
public abstract class HibernateDao<T> implements Dao<T> {
    private Class<T> persistentClass;
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    HibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.persistentClass = (Class<T>) GenericTypeResolver
                .resolveTypeArgument(getClass(), HibernateDao.class);
    }

    Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<T> findAll() throws DaoException {
        try {
            Query<T> query = getSession().createQuery(
                    "from " + persistentClass.getSimpleName(), persistentClass
            );
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public T findById(int id) throws DaoException {
        try {
            return getSession().get(persistentClass, id);
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void deleteAll() throws DaoException {
        try {
            Query query = getSession().createQuery("delete from " + persistentClass.getSimpleName());
            query.executeUpdate();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
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

    private void execute(BiConsumer<Session, T> biConsumer, T object) throws DaoException {
        try {
            biConsumer.accept(getSession(), object);
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }
}
