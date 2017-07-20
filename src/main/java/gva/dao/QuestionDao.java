package gva.dao;

import gva.exception.DaoException;
import gva.model.Question;
import gva.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionDao extends Dao<Question> {
    List<Question> findTop(int count) throws DaoException;

    List<Question> findBetween(LocalDateTime from, LocalDateTime to) throws DaoException;

    List<Question> findAskedBy(User author) throws DaoException;
}
