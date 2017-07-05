package gva.dao.impl;

import gva.dao.QuestionDao;
import gva.exception.DaoException;
import gva.model.Question;
import gva.model.User;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class HibernateQuestionDao extends HibernateDao<Question> implements QuestionDao {

    @Autowired
    public HibernateQuestionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Question> getAll() throws DaoException {
        try {
            Query<Question> query = getSession().createQuery(
                    "from Question", Question.class
            );
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public Question getById(int id) throws DaoException {
        try {
            return getSession().get(Question.class, id);
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Question> getTop(int count) throws DaoException {
        try {
            Query<Question> query = getSession().createQuery(
                    "from Question order by rating desc", Question.class
            ).setMaxResults(count);
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Question> getBetween(Date from, Date to) throws DaoException {
        try {
            Query<Question> query = getSession().createQuery(
                    "from Question where timeStamp between :fromDate and :toDate", Question.class
            );
            query.setParameter("fromDate", from, TemporalType.TIMESTAMP)
                    .setParameter("toDate", to, TemporalType.TIMESTAMP);
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Question> getAskedBy(User author) throws DaoException {
        try {
            Query<Question> query = getSession().createQuery(
                    "from Question where author.id = :authorId", Question.class
            ).setParameter("authorId", author.getId());
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }
}