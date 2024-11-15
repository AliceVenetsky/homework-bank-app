package dev.venetsky.operation;

import dev.venetsky.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class WithdrawAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;

    public WithdrawAccountCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }


    @Override
    public void executeCommand() {
        System.out.println("Enter account id:");
        long accountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter money amount:");
        int moneyAmount = Integer.parseInt(scanner.nextLine());
        accountService.withdrawMoneyFromAccount(accountId, moneyAmount);
        System.out.printf("Money %s withdrawn from account id %s", moneyAmount, accountId);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_WITHDRAW;
    }
}
