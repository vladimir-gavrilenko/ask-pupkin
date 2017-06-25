package gva.dao;

import gva.exception.DaoException;
import gva.model.Answer;
import gva.model.Question;

import java.util.Date;
import java.util.List;

public interface AnswerDao extends Dao<Answer> {
    List<Answer> getBetween(Date from, Date to) throws DaoException;

    List<Answer> getFor(Question question) throws DaoException;
}
