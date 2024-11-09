package dev.venetsky.operation;

import dev.venetsky.model.User;
import dev.venetsky.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllUsersCommand implements OperationCommand{
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void executeCommand() {
        List<User> users =  userService.getAllUsers();
        System.out.println("List of all users:");
        users.forEach(user -> System.out.println(user));
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.SHOW_ALL_USERS;
    }
}
