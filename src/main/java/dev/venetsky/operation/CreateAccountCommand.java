package dev.venetsky.operation;

import dev.venetsky.model.User;
import dev.venetsky.service.AccountService;
import dev.venetsky.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateAccountCommand implements OperationCommand{

    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountCommand(
            Scanner scanner,
            UserService userService,
            AccountService accountService
    ) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void executeCommand() {
        System.out.println("Enter user id for which to create an account:");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user with id %s".formatted(userId)));
        accountService.createAccount(user);
        System.out.println("New account created for user with id %s".formatted(userId));
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CREATE;
    }
}
