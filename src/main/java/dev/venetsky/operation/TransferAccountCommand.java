package dev.venetsky.operation;

import dev.venetsky.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TransferAccountCommand implements OperationCommand{

    private final Scanner scanner;
    private final AccountService accountService;

    public TransferAccountCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void executeCommand() {
        System.out.println("Enter account id to transfer from:");
        int accountFrom = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter account id to transfer to:");
        int accountTo = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter money amount to transfer:");
        int money = Integer.parseInt(scanner.nextLine());
        accountService.transferMoney(accountFrom, accountTo, money);
        System.out.printf("Successfully transfered money from account %s to account %s%n", accountFrom, accountTo);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_TRANSFER;
    }
}
