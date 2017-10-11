package gva.service.impl;

import gva.dao.UserDao;
import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;
import gva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public void create(User user) throws UsernameExistsException, EmailExistsException {
        if (userDao.findByName(user.getName()) != null) {
            throw new UsernameExistsException("failed to create user with name: " + user.getName());
        }
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("failed to create user with email: " + user.getEmail());
        }
        userDao.create(user);
    }

    @Override
    public void update(User user) throws UsernameExistsException, EmailExistsException {
        User oldUser = userDao.findById(user.getId());
        if (!oldUser.getName().equals(user.getName()) && userDao.findByName(user.getName()) != null) {
            throw new UsernameExistsException("failed to update user with new name: " + user.getName());
        }
        if (!oldUser.getEmail().equals(user.getEmail()) && userDao.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("failed to update user with new email: " + user.getEmail());
        }
        userDao.update(user);
    }

    @Override
    public String encodePassword(String password) {
        return encoder.encode(password);
    }
}
