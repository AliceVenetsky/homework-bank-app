package dev.venetsky.service;

import dev.venetsky.model.Account;
import dev.venetsky.model.AccountProperties;
import dev.venetsky.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
    private int accountCounter;
    private final Map<Integer, Account> accounts;
    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accountCounter = 0;
        this.accounts = new HashMap<>();
        this.accountProperties = accountProperties;

    }

    public Account createAccount(User user) {
        accountCounter++;
        Account account = new Account(
                accountCounter,
                user.getUserId(),
                accountProperties.getDefaultAccountMoney()
        );
        user.getAccountList().add(account);
        accounts.put(account.getAccountId(), account);
        return account;
    }

    public Optional<Account> findAccountById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public List<Account> getAllUserAccounts(int userId) {
        return accounts.values()
                .stream()
                .filter(account -> account.getUserId() == userId)
                .toList();
    }

    public void addMoneyToAccount(int accountId, int money) {
        checkMoneyAmount(money);
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist!"));
        account.setMoneyAmount(account.getMoneyAmount() + money);
    }

    public void withdrawMoneyFromAccount(int accountId, int money) {
        checkMoneyAmount(money);
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist!"));
        if (account.getMoneyAmount() < money) {
            throw new IllegalArgumentException("Not enough money for withdrawal!");
        }
        account.setMoneyAmount(account.getMoneyAmount() - money);
    }

    public Account closeAccount(int accountId) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist!"));
        List<Account> accountList = getAllUserAccounts(account.getUserId());
        if (accountList.size() == 1)
            throw new IllegalArgumentException("Can't close only one account!");

        Account accountToDeposit = accountList.stream()
                .filter(acc -> acc.getAccountId() != accountId)
                .findFirst()
                .orElseThrow();
        accountToDeposit.setMoneyAmount(accountToDeposit.getMoneyAmount() + account.getMoneyAmount());
        accounts.remove(accountId);
        return account;
    }

    private void checkMoneyAmount(int money) {
        if (money <= 0)
            throw new IllegalArgumentException("Illegal money amount! Money amount must be positive!");
    }

    public void transferMoney(int accountFrom, int accountTo, int money) {
        checkMoneyAmount(money);
        Account accountTransferFrom = findAccountById(accountFrom)
                .orElseThrow(() -> new IllegalArgumentException("Transfer from account  doesn't exist!"));
        Account accountTransferTo = findAccountById(accountTo)
                .orElseThrow(() -> new IllegalArgumentException("Transfer to account doesn't exist!"));
        if (accountTransferFrom.getMoneyAmount() < money) {
            throw new IllegalArgumentException("Not enough money for withdrawal!");
        }
        int amountToDeposit = accountTransferFrom.getUserId() != accountTransferTo.getUserId()
                ? (int) (money - money * accountProperties.getTransferCommission())
                : money;

        accountTransferFrom.setMoneyAmount(accountTransferFrom.getMoneyAmount() - money);
        accountTransferTo.setMoneyAmount(accountTransferTo.getMoneyAmount() + amountToDeposit);
    }
}
