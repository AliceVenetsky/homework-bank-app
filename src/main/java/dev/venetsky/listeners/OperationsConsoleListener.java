package dev.venetsky.listeners;


import dev.venetsky.operation.OperationCommand;
import dev.venetsky.operation.OperationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {

    private final Scanner scanner;
    private final Map<OperationType, OperationCommand>commandMap;

    public OperationsConsoleListener(
            Scanner scanner,
            List<OperationCommand>commandList
    ) {
        this.scanner = scanner;
        this.commandMap =
                commandList
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        command -> command.getOperationType(),
                                        command -> command
                                )
                        );
    }
    public void start() {
        System.out.println("Console listener started.");
    }
    public void listenConsole() {
        System.out.println("Please select operation:");
        while(!Thread.currentThread().isInterrupted()) {
            OperationType operationType = null;
            try {
                operationType = listenNextOperation();
                if(operationType == null)
                    return;
                executeNextOperation(operationType);
            } catch (Exception ex) {
                System.out.println("Error with executing command %s\n".formatted(operationType));
            }
        }
    }
    public OperationType listenNextOperation() {
        printAllAvailableOperations();
        while(!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("Please enter next operation:");
                return OperationType.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException exception) {
                System.out.println("No such command found!");
            }
        }
        return null;
    }
    public void executeNextOperation(OperationType operationType) {
        try {
            OperationCommand command  = commandMap.get(operationType);
            command.executeCommand();
        } catch (Exception ex) {
            System.out.println("Error with executing command %s\n".formatted(operationType));
        }
    }
    public void printAllAvailableOperations() {
        commandMap.keySet().stream().forEach(key -> System.out.println(key));
    }

    public void end() {
        System.out.println("Console listener closed.");
    }

}
