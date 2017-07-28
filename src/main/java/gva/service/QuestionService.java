package gva.service;

import gva.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findTop(int count);
    List<Question> findNew();
    void create(Question question);
}
