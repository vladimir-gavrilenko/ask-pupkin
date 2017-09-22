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

import javax.persistence.NoResultException;

@Repository
@Transactional
public class HibernateUserDao extends HibernateDao<User> implements UserDao {

    @Autowired
    HibernateUserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User findByName(String name) throws DaoException {
        return findBy(User.NAME, name);
    }

    @Override
    public User findByEmail(String email) throws DaoException {
        return findBy(User.EMAIL, email);
    }

    private User findBy(String field, String value) throws DaoException {
        try {
            Query<User> query = getSession().createQuery(
                    "from User where " + field + " = :value", User.class
            );
            query.setParameter("value", value);
            return query.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }
}
