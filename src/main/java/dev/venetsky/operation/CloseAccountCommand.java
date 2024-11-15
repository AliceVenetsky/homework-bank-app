package dev.venetsky.operation;

import dev.venetsky.model.Account;
import dev.venetsky.model.User;
import dev.venetsky.service.AccountService;
import dev.venetsky.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {

    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public CloseAccountCommand(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void executeCommand() {
        System.out.println("Enter account id to close:");
        long accountId = Long.parseLong(scanner.nextLine());
        accountService.closeAccount(accountId);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CLOSE;
    }
}
