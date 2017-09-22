package gva.service;

import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;

public interface UserService {
    User findByName(String name);

    void create(User user) throws UsernameExistsException, EmailExistsException;
}
