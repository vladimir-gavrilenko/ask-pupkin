package gva.dao;

import gva.exception.DaoException;
import gva.model.Answer;
import gva.model.Question;
import gva.model.User;

class DaoTest {
    User foo, bar, baz;
    Question questionByFoo;
    Answer answerByBar, answerByBaz;
    private static final String HASH_STRING = "12345678901234567890123456789012";

    DaoTest() {
        foo = new User("foo", "foo@test.com", HASH_STRING);
        bar = new User("bar", "bar@test.com", HASH_STRING);
        baz = new User("baz", "baz@test.com", HASH_STRING);
        questionByFoo = new Question("Header", "Question by foo", foo);
        answerByBar = new Answer("Answer by bar", bar, questionByFoo);
        answerByBaz = new Answer("Answer by baz", baz, questionByFoo);
    }

    void initTestData(UserDao userDao, QuestionDao questionDao, AnswerDao answerDao)
            throws Exception {
        userDao.create(foo);
        userDao.create(bar);
        userDao.create(baz);
        questionDao.create(questionByFoo);
        answerDao.create(answerByBar);
        answerDao.create(answerByBaz);
    }


    void clearTestData(UserDao userDao, QuestionDao questionDao, AnswerDao answerDao)
            throws DaoException {
        answerDao.deleteAll();
        questionDao.deleteAll();
        userDao.deleteAll();
    }
}
