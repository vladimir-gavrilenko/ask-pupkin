package gva.service.impl;

import gva.dao.AnswerDao;
import gva.dao.QuestionDao;
import gva.exception.DaoException;
import gva.model.Answer;
import gva.model.Question;
import gva.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QAServiceImpl implements QAService {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Autowired
    public QAServiceImpl(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public List<Question> findTopQuestions(int count) {
        try {
            return questionDao.findTop(count);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Question> findNewQuestions() {
        try {
            return questionDao.findNew();
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Question findQuestionById(int id) {
        try {
            return questionDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Answer> findAnswersFor(Question question) {
        try {
            return answerDao.findFor(question);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }
}
