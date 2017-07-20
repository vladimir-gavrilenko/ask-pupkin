package gva.dao;
import gva.config.RootConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = {RootConfig.class})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class DeleteDaoTest extends DaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private UserDao userDao;

    @Ignore // TODO what about cascade removing?
    @Test
    public void testDeleteDataFromDb() throws Exception {
        initTestData(userDao, questionDao, answerDao);

        assertTrue(answerDao.findAll().contains(answerByBar));
        answerDao.delete(answerByBar);
        assertFalse(answerDao.findAll().contains(answerByBar));

        assertTrue(questionDao.findAll().contains(questionByFoo));
        questionDao.delete(questionByFoo);
        assertFalse(questionDao.findAll().contains(questionByFoo));

        assertTrue(userDao.findAll().contains(foo));
        userDao.delete(foo);
        assertFalse(userDao.findAll().contains(foo));
    }
}
