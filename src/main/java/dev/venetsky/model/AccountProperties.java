package dev.venetsky.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final int defaultAccountMoney;
    private final double transferCommission;

    public int getDefaultAccountMoney() {
        return defaultAccountMoney;
    }

    public double getTransferCommission() {
        return transferCommission;
    }

    public AccountProperties(
            @Value("${account.default-amount}") int defaultAccountMoney,
            @Value("${account.transfer-commission}") double transferCommission) {
        this.defaultAccountMoney = defaultAccountMoney;
        this.transferCommission = transferCommission;
    }
}

