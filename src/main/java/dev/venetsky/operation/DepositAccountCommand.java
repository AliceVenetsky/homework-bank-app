package dev.venetsky.operation;

import dev.venetsky.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DepositAccountCommand implements OperationCommand {

    private final AccountService accountService;
    private final Scanner scanner;

    public DepositAccountCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void executeCommand() {
        System.out.println("Enter account id:");
        long accountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter money amount:");
        int moneyAmount = Integer.parseInt(scanner.nextLine());
        accountService.addMoneyToAccount(accountId, moneyAmount);
        System.out.println("Money %s deposited to account id %s"
                .formatted(moneyAmount, accountId));
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_DEPOSIT;
    }
}
