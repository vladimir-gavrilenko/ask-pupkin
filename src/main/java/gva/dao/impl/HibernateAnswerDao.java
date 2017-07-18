package gva.dao.impl;

import gva.dao.AnswerDao;
import gva.exception.DaoException;
import gva.model.Answer;
import gva.model.Question;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class HibernateAnswerDao extends HibernateDao<Answer> implements AnswerDao {

    @Autowired
    public HibernateAnswerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Answer> getAll() throws DaoException {
        try {
            Query<Answer> query = getSession().createQuery(
                    "from Answer", Answer.class
            );
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public Answer getById(int id) throws DaoException {
        try {
            return getSession().get(Answer.class, id);
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public void deleteAll() throws DaoException {
        try {
            Query query = getSession().createQuery("delete from Answer");
            query.executeUpdate();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Answer> getBetween(LocalDateTime from, LocalDateTime to) throws DaoException {
        try {
            Query<Answer> query = getSession().createQuery(
                    "from Answer where timeStamp between :fromDate and :toDate", Answer.class
            );
            query.setParameter("fromDate", from)
                    .setParameter("toDate", to);
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Answer> getFor(Question question) throws DaoException {
        try {
            Query<Answer> query = getSession().createQuery(
                    "from Answer where question.id = :questionId", Answer.class
            ).setParameter("questionId", question.getId());
            return query.getResultList();
        } catch (HibernateException exception) {
            throw new DaoException(exception);
        }
    }
}
