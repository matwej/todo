package sk.pivarci.todo.service;

import sk.pivarci.todo.model.User;

public interface UserService {
    User save(User user);
    User findUserByLogin(String login);
}
