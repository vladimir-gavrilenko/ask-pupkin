package gva.service;

import gva.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getTop(int count);
    void create(Question question);
}
