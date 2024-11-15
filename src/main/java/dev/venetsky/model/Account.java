package dev.venetsky.model;

import jakarta.persistence.*;

import java.lang.annotation.Target;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "money_amount")
    private Integer moneyAmount;

    public Account() {
    }

    public Account(Long accountId, User user, int defaultMoneyAmount) {

        this.accountId = accountId;
        this.user = user;
        this.moneyAmount = defaultMoneyAmount;
    }

    public User getUser() {
        return user;
    }

    public long getAccountId() {
        return accountId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
