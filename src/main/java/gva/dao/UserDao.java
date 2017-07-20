package gva.dao;

import gva.exception.DaoException;
import gva.model.User;

public interface UserDao extends Dao<User> {
    User findByName(String name) throws DaoException;

    User findByEmail(String email) throws DaoException;
}
