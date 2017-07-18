package gva.dao;

import gva.exception.DaoException;
import gva.model.Answer;
import gva.model.Question;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerDao extends Dao<Answer> {
    List<Answer> getBetween(LocalDateTime from, LocalDateTime to) throws DaoException;

    List<Answer> getFor(Question question) throws DaoException;
}
