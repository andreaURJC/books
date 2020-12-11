package com.urjc.books.services;

import com.urjc.books.models.User;
import com.urjc.books.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

    public Optional<List<User>> findAll() {
        return this.userRepository.findAll();
    }

    public Optional<User> findUserByNick(int nick) {
        return this.userRepository.findUserByNick(nick);
    }

    public Optional<User> modifyEmail(User user) {
        return this.userRepository.modifyEmail(user);
    }
}
