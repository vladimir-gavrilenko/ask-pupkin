package gva.service;

import gva.model.Answer;
import gva.model.Question;

import java.util.List;

public interface QAService {
    List<Question> findTopQuestions(int count);

    List<Question> findNewQuestions();

    Question findQuestionById(int id);

    List<Answer> findAnswersFor(Question question);
}
