package com.urjc.books.repositories;

import com.urjc.books.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {
    private AtomicInteger atomicInt = new AtomicInteger();
    private ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public void save(User user) {
        int nick = this.getIdAndAdd();
        user.setNick(nick);

        users.put(nick, user);
    }

    public Optional<User> findUserByNick(int nick) {
        return Optional.of(this.users.get(nick));
    }

    public Optional<List<User>> findAll() {
        return Optional.of(new ArrayList<>(this.users.values()));
    }

    public Optional<User> modifyEmail(User user) {
        Optional<User> existingUser = this.findUserByNick(user.getNick());
        if (existingUser.isPresent()) {
            this.users.put(user.getNick(), user);
        }
        return this.findUserByNick(user.getNick());
    }

    //TODO --> delete User solo si no tiene comentarios asociados

    private int getIdAndAdd() {
        return this.atomicInt.getAndAdd(1);
    }

}
