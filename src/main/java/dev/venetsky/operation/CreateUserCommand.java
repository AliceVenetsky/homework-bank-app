package dev.venetsky.operation;

import dev.venetsky.model.User;
import dev.venetsky.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {

    private final Scanner scanner;
    private final UserService userService;

    public CreateUserCommand(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void executeCommand() {
        System.out.println("Enter login for new user:");
        String login = scanner.nextLine();
        User user = userService.createUser(login);
        System.out.println("User %s created:".formatted(user.toString()));
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.USER_CREATE;
    }
}
