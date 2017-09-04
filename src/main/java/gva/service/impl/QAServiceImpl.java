package gva.service.impl;

import gva.dao.AnswerDao;
import gva.dao.QuestionDao;
import gva.model.Answer;
import gva.model.Question;
import gva.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QAServiceImpl implements QAService {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    @Autowired
    public QAServiceImpl(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public List<Question> findTopQuestions(int count) {
        return questionDao.findTop(count);
    }

    @Override
    public List<Question> findNewQuestions() {
        return questionDao.findNew();
    }

    @Override
    public Question findQuestionById(int id) {
        return questionDao.findById(id);
    }

    @Override
    public List<Answer> findAnswersFor(Question question) {
        return answerDao.findFor(question);
    }
}
