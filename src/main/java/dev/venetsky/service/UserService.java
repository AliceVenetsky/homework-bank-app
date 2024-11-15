package dev.venetsky.service;

import dev.venetsky.hibernate.TransactionHelper;
import dev.venetsky.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final SessionFactory sessionFactory;
    private final AccountService accountService;
    private final TransactionHelper transactionHelper;

    public UserService(
            SessionFactory sessionFactory,
            AccountService accountService,
            TransactionHelper transactionHelper
    ) {
        this.sessionFactory = sessionFactory;
        this.accountService = accountService;
        this.transactionHelper = transactionHelper;
    }

    public User createUser(String login) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();

            User user = session.createQuery("SELECT u FROM User u WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResultOrNull();
            if (user != null)
                throw new IllegalArgumentException("User with login %s is already exists!".formatted(login));

            User newUser = new User(null, login, new ArrayList<>());
            session.persist(newUser);

            accountService.createAccount(newUser);
            return newUser;
        });
    }

    public Optional<User> findUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT u FROM User u JOIN FETCH u.accountList",
                            User.class
                    )
                    .list();
        }
    }
}
