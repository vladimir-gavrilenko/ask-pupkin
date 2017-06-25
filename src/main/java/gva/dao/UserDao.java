package gva.dao;

import gva.exception.DaoException;
import gva.model.User;

public interface UserDao extends Dao<User> {
    User getByName(String name) throws DaoException;

    User getByEmail(String email) throws DaoException;
}
