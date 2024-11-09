package dev.venetsky.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String login;
    private final int userId;
    private final List<Account> accountList;

    public User(int id, String login, List<Account> list) {
        this.login = login;
        this.userId = id;
        this.accountList = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public int getUserId() {
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
