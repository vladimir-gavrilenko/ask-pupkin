package gva.service;

import gva.exception.EmailExistsException;
import gva.exception.UsernameExistsException;
import gva.model.User;

public interface UserService {
    User findById(Long id);

    User findByName(String name);

    void create(User user) throws UsernameExistsException, EmailExistsException;

    void update(User user) throws UsernameExistsException, EmailExistsException;

    String encodePassword(String password);

    void updateAvatarPath(User user, String fileName);
}
