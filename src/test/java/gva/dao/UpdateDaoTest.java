package gva.dao;

import gva.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {RootConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class UpdateDaoTest extends DaoTest {
    private static final String FOO_STRING = "FOO";

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void testUpdateDataInDb() throws Exception {
        initTestData(userDao, questionDao, answerDao);

        foo.setName(FOO_STRING);
        userDao.update(foo);
        assertEquals(FOO_STRING, userDao.findById(foo.getId()).getName());

        questionByFoo.setContent(FOO_STRING);
        questionDao.update(questionByFoo);
        assertEquals(FOO_STRING, questionDao.findById(questionByFoo.getId()).getContent());

        boolean answerByBarWasCorrect = answerByBar.isCorrect();
        answerByBar.setCorrect(!answerByBarWasCorrect);
        answerDao.update(answerByBar);
        assertEquals(!answerByBarWasCorrect, answerDao.findById(answerByBar.getId()).isCorrect());
    }
}
