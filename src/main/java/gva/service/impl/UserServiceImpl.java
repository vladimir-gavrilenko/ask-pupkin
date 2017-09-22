package gva.service.impl;

import gva.dao.UserDao;
import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;
import gva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public void create(User user) throws UsernameExistsException, EmailExistsException {
        if (userDao.findByName(user.getName()) != null) {
            throw new UsernameExistsException("name: " + user.getName());
        }
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("email: " + user.getEmail());
        }
        userDao.create(user);
    }
}
