package gva.dao;

import gva.exception.DaoException;
import gva.model.Question;
import gva.model.User;

import java.util.Date;
import java.util.List;

public interface QuestionDao extends Dao<Question> {
    List<Question> getTopUntil(Date until) throws DaoException;

    List<Question> getBetween(Date from, Date to) throws DaoException;

    List<Question> getAskedBy(User author) throws DaoException;
}
