package dev.venetsky.model;

import jakarta.persistence.*;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_login", unique = true)
    private String login;

    @OneToMany(mappedBy = "user")
    private List<Account> accountList;

    public User() {
    }

    public User(Long id, String login, List<Account> list) {
        this.login = login;
        this.userId = id;
        this.accountList = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", userId=" + userId +
                ", accountList=" + accountList +
                '}';
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}
