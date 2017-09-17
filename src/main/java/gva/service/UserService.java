package gva.service;

import gva.model.User;

public interface UserService {
    User findByName(String name);

    void create(User user);
}
