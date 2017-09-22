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

import static org.junit.Assert.*;

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
        List<User> users = userDao.findAll();
        assertTrue(users.contains(foo));
        assertTrue(users.contains(bar));
        assertTrue(users.contains(baz));
        assertEquals(3, users.size());

        List<Question> questions = questionDao.findAll();
        assertTrue(questions.contains(questionByFoo));
        assertEquals(1, questions.size());

        List<Answer> answers = answerDao.findAll();
        assertTrue(answers.contains(answerByBar));
        assertTrue(answers.contains(answerByBaz));
        assertEquals(2, answers.size());
    }

    @Test
    public void testUsers() throws Exception {
        assertEquals(foo, userDao.findByEmail(foo.getEmail()));
        assertEquals(bar, userDao.findByName(bar.getName()));
    }

    @Test
    public void testAnswers() throws Exception {
        List<Answer> answers;

        answers = answerDao.findFor(answerByBar.getQuestion());
        assertTrue(answers.contains(answerByBar));

        LocalDateTime bazAnsweredAt = answerByBaz.getTimeStamp();
        answers = answerDao.findBetween(bazAnsweredAt.minusDays(1), bazAnsweredAt.plusDays(1));
        assertTrue(answers.contains(answerByBaz));
        answers = answerDao.findBetween(bazAnsweredAt.minusDays(2), bazAnsweredAt.minusDays(1));
        assertFalse(answers.contains(answerByBaz));
        answers = answerDao.findBetween(bazAnsweredAt.plusDays(1), bazAnsweredAt.plusDays(2));
        assertFalse(answers.contains(answerByBaz));
    }

    @Test
    public void testQuestions() throws Exception {
        List<Question> questions;

        questions = questionDao.findAskedBy(foo);
        assertTrue(questions.contains(questionByFoo));

        LocalDateTime fooAskedAt = questionByFoo.getTimeStamp();
        questions = questionDao.findBetween(fooAskedAt.minusDays(1), fooAskedAt.plusDays(1));
        assertTrue(questions.contains(questionByFoo));
        questions = questionDao.findBetween(fooAskedAt.minusDays(2), fooAskedAt.minusDays(1));
        assertFalse(questions.contains(questionByFoo));
        questions = questionDao.findBetween(fooAskedAt.plusDays(1), fooAskedAt.plusDays(2));
        assertFalse(questions.contains(questionByFoo));

        questions = questionDao.findTop(Integer.MAX_VALUE);
        assertEquals(questions, questionDao.findAll());
        questions = questionDao.findTop(5);
        assertTrue(questions.size() < 5);
        questions = questionDao.findTop(0);
        assertTrue(questions.isEmpty());
    }

    @Test
    public void testFindingNonExistingRecords() throws Exception {
        User user = userDao.findByName("-1");
        assertNull(user);
        user = userDao.findByEmail("-1");
        assertNull(user);
        user = userDao.findById(-1L);
        assertNull(user);

        Question question = questionDao.findById(-1L);
        assertNull(question);
        user = new User("test", "test", "test");
        List<Question> questions = questionDao.findAskedBy(user);
        assertTrue(questions.isEmpty());

        Answer answer = answerDao.findById(-1L);
        assertNull(answer);
        question = new Question("test", "test", user);
        List<Answer> answers = answerDao.findFor(question);
        assertTrue(answers.isEmpty());
    }
}
