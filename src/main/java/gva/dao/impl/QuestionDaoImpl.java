package gva.dao.impl;

import gva.dao.QuestionDao;
import gva.exception.DaoException;
import gva.model.Question;
import gva.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class QuestionDaoImpl implements QuestionDao {
    private SessionFactory sessionFactory;

    @Autowired
    public QuestionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Question object) throws DaoException {

    }

    @Override
    public List<Question> getAll() throws DaoException {
        return null;
    }

    @Override
    public Question getById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Question object) throws DaoException {

    }

    @Override
    public void delete(Question object) throws DaoException {

    }

    @Override
    public List<Question> getTop(int count) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Question");
        List<Question> questions = query.list();
        return questions;
    }

    @Override
    public List<Question> getBetween(Date from, Date to) throws DaoException {
        return null;
    }

    @Override
    public List<Question> getAskedBy(User author) throws DaoException {
        return null;
    }
}
