package dev.venetsky.operation;

public interface OperationCommand {

    void executeCommand();
    OperationType getOperationType();
}
