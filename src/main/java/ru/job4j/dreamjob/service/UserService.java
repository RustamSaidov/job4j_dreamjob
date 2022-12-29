package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDBStore;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public List<User> findAll() {
        return store.findAll();
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public User findById(Integer id) {
        return store.findById(id);
    }

    public void update(User user) {
        store.update(user);
    }
}
