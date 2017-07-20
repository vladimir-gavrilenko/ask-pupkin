package gva.service.impl;

import gva.dao.QuestionDao;
import gva.exception.DaoException;
import gva.model.Question;
import gva.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionDao questionDao;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getTop(int count) {
        try {
            return questionDao.findTop(count);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void create(Question question) {
        try {
            questionDao.create(question);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
