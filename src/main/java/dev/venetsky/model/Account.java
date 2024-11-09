package dev.venetsky.model;

public class Account {

    private final int userId;
    private final int accountId;

    public long getMoneyAmount() {
        return moneyAmount;
    }

    private long moneyAmount;

    public Account(int accountId, int userId, int defaultMoneyAmount) {

        this.accountId = accountId;
        this.userId = userId;
        this.moneyAmount = defaultMoneyAmount;
    }

    public int getUserId() {
        return userId;
    }
    public int getAccountId() {
        return accountId;
    }

    public void setMoneyAmount(long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
