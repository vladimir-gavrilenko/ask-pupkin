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
public class CreateDaoTest extends DaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsertDataIntoDb() throws Exception {
        initTestData(userDao, questionDao, answerDao);

        assertEquals(foo, userDao.getById(foo.getId()));
        assertEquals(bar, userDao.getById(bar.getId()));
        assertEquals(baz, userDao.getById(baz.getId()));

        assertEquals(questionByFoo, questionDao.getById(questionByFoo.getId()));

        assertEquals(answerByBar, answerDao.getById(answerByBar.getId()));
        assertEquals(answerByBaz, answerDao.getById(answerByBaz.getId()));

        clearTestData(userDao, questionDao, answerDao);
    }
}
