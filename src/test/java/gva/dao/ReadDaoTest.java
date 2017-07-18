package gva.dao;

import gva.config.RootConfig;
import gva.model.Answer;
import gva.model.Question;
import gva.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = {RootConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class ReadDaoTest extends DaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        initTestData(userDao, questionDao, answerDao);
    }

    @After
    public void tearDown() throws Exception {
        clearTestData(userDao, questionDao, answerDao);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> users = userDao.getAll();
        assertTrue(users.contains(foo));
        assertTrue(users.contains(bar));
        assertTrue(users.contains(baz));
        assertEquals(3, users.size());

        List<Question> questions = questionDao.getAll();
        assertTrue(questions.contains(questionByFoo));
        assertEquals(1, questions.size());

        List<Answer> answers = answerDao.getAll();
        assertTrue(answers.contains(answerByBar));
        assertTrue(answers.contains(answerByBaz));
        assertEquals(2, answers.size());
    }

    @Test
    public void testUsers() throws Exception {
        assertEquals(foo, userDao.getByEmail(foo.getEmail()));
        assertEquals(bar, userDao.getByName(bar.getName()));
    }

    @Test
    public void testAnswers() throws Exception {
        List<Answer> answers;

        answers = answerDao.getFor(answerByBar.getQuestion());
        assertTrue(answers.contains(answerByBar));

        LocalDateTime bazAnsweredAt = answerByBaz.getTimeStamp();
        answers = answerDao.getBetween(bazAnsweredAt.minusDays(1), bazAnsweredAt.plusDays(1));
        assertTrue(answers.contains(answerByBaz));
        answers = answerDao.getBetween(bazAnsweredAt.minusDays(2), bazAnsweredAt.minusDays(1));
        assertFalse(answers.contains(answerByBaz));
        answers = answerDao.getBetween(bazAnsweredAt.plusDays(1), bazAnsweredAt.plusDays(2));
        assertFalse(answers.contains(answerByBaz));
    }

    @Test
    public void testQuestions() throws Exception {
        List<Question> questions;

        questions = questionDao.getAskedBy(foo);
        assertTrue(questions.contains(questionByFoo));

        LocalDateTime fooAskedAt = questionByFoo.getTimeStamp();
        questions = questionDao.getBetween(fooAskedAt.minusDays(1), fooAskedAt.plusDays(1));
        assertTrue(questions.contains(questionByFoo));
        questions = questionDao.getBetween(fooAskedAt.minusDays(2), fooAskedAt.minusDays(1));
        assertFalse(questions.contains(questionByFoo));
        questions = questionDao.getBetween(fooAskedAt.plusDays(1), fooAskedAt.plusDays(2));
        assertFalse(questions.contains(questionByFoo));

        questions = questionDao.getTop(Integer.MAX_VALUE);
        assertEquals(questions, questionDao.getAll());
        questions = questionDao.getTop(5);
        assertTrue(questions.size() < 5);
        questions = questionDao.getTop(0);
        assertTrue(questions.isEmpty());
    }
}
