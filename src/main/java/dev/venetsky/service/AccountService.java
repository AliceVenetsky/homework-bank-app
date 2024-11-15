package dev.venetsky.service;

import dev.venetsky.hibernate.TransactionHelper;
import dev.venetsky.model.Account;
import dev.venetsky.model.AccountProperties;
import dev.venetsky.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final SessionFactory sessionFactory;
    private final AccountProperties accountProperties;
    private final TransactionHelper transactionHelper;

    public AccountService(
            SessionFactory sessionFactory,
            AccountProperties accountProperties,
            TransactionHelper transactionHelper
    ) {
        this.accountProperties = accountProperties;
        this.sessionFactory = sessionFactory;
        this.transactionHelper = transactionHelper;
    }

    public Account createAccount(User user) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            Account newAccount = new Account(
                    null,
                    user,
                    accountProperties.getDefaultAccountMoney()
            );
            session.persist(newAccount);

            return newAccount;
        });
    }

    private Optional<Account> findAccountById(Long id) {
        Account account = sessionFactory.getCurrentSession().get(Account.class, id);
        return Optional.ofNullable(account);
    }

    public void addMoneyToAccount(Long accountId, int money) {
        checkMoneyAmount(money);
        transactionHelper.executeInTransaction(() -> {
            Account account = findAccountById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist!"));

            account.setMoneyAmount(account.getMoneyAmount() + money);
            return account;
        });
    }

    public void withdrawMoneyFromAccount(Long accountId, int money) {
        checkMoneyAmount(money);

        transactionHelper.executeInTransaction(() -> {
            Account account = findAccountById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist!"));
            if (account.getMoneyAmount() < money) {
                throw new IllegalArgumentException("Not enough money for withdrawal!");
            }

            account.setMoneyAmount(account.getMoneyAmount() - money);
            return account;
        });

    }

    public Account closeAccount(Long accountId) {

        return transactionHelper.executeInTransaction(() -> {
            Account accountToClose = findAccountById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist!"));

            if (accountToClose.getUser().getAccountList().size() == 1)
                throw new IllegalArgumentException("Can't close only one account!");

            Account accountToDeposit = accountToClose.getUser().getAccountList()
                    .stream()
                    .filter(acc -> acc.getAccountId() != accountId)
                    .findFirst()
                    .orElseThrow();

            accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + accountToClose.getMoneyAmount());

            sessionFactory.getCurrentSession().remove(accountToClose);

            return accountToClose;
        });
    }

    private void checkMoneyAmount(int money) {
        if (money <= 0)
            throw new IllegalArgumentException("Illegal money amount! Money amount must be positive!");
    }

    public void transferMoney(Long accountFrom, Long accountTo, int money) {
        checkMoneyAmount(money);

        transactionHelper.executeInTransaction(() -> {
            Account accountTransferFrom = findAccountById(accountFrom)
                    .orElseThrow(() -> new IllegalArgumentException("Transfer from account  doesn't exist!"));
            Account accountTransferTo = findAccountById(accountTo)
                    .orElseThrow(() -> new IllegalArgumentException("Transfer to account doesn't exist!"));
            if (accountTransferFrom.getMoneyAmount() < money) {
                throw new IllegalArgumentException("Not enough money for withdrawal!");
            }

            int amountToDeposit = accountTransferFrom.getUser().getUserId() != accountTransferTo.getUser().getUserId()
                    ? (int) (money - money * accountProperties.getTransferCommission())
                    : money;

            accountTransferFrom.setMoneyAmount(accountTransferFrom.getMoneyAmount() - money);
            accountTransferTo.setMoneyAmount(accountTransferTo.getMoneyAmount() + amountToDeposit);
            return null;
        });
    }
}
