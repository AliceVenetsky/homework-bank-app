package dev.venetsky.service;

import dev.venetsky.model.Account;
import dev.venetsky.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<Integer, User> users;
    private int idCounter;
    private final Set<String> logins;

    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.users = new HashMap<>();
        this.idCounter = 0;
        this.logins = new HashSet<>();
        this.accountService = accountService;
    }

    public User createUser(String login) {
        if(logins.contains(login))
            throw new IllegalArgumentException("User with login %s is already exists!".formatted(login));
        logins.add(login);
        idCounter ++;
        User user  = new User(idCounter, login, new ArrayList<>());
        users.put(idCounter, user);
        Account account = accountService.createAccount(user);
        return user;
    }
    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }
}
