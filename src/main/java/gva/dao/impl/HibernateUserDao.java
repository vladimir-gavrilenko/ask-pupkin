package gva.dao.impl;

import gva.dao.UserDao;
import gva.exception.DaoException;
import gva.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HibernateUserDao extends HibernateDao<User> implements UserDao {

    @Autowired
    HibernateUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<User> getAll() throws DaoException {
        try {
            Query<User> query = getSession().createQuery(
                    "from User", User.class
            );
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public User getById(int id) throws DaoException {
        try {
            return getSession().get(User.class, id);
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }


    @Override
    public void deleteAll() throws DaoException {
        try {
            Query query = getSession().createQuery("delete from User");
            query.executeUpdate();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public User getByName(String name) throws DaoException {
        return getBy(User.NAME, name);
    }

    @Override
    public User getByEmail(String email) throws DaoException {
        return getBy(User.EMAIL, email);
    }

    private User getBy(String field, String value) throws DaoException {
        try {
            Query<User> query = getSession().createQuery(
                    "from User where " + field + " = :value", User.class
            );
            query.setParameter("value", value);
            return query.getSingleResult();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }
}
