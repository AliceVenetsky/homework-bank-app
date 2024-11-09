package dev.venetsky;

import dev.venetsky.service.OperationsConsoleListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListenerStarter {

    private final OperationsConsoleListener operationsConsoleListener;
    private Thread consoleListenerThread;

    public ConsoleListenerStarter(OperationsConsoleListener operationsConsoleListener) {
        this.operationsConsoleListener = operationsConsoleListener;
    }

    @PostConstruct
    public void postConstruct() {
        this.consoleListenerThread = new Thread(() -> {
            operationsConsoleListener.start();
            operationsConsoleListener.listenConsole();
        });
        consoleListenerThread.start();
    }

    @PreDestroy
    public void preDestroy() {
        if(consoleListenerThread.isAlive())
            consoleListenerThread.interrupt();
        operationsConsoleListener.end();
    }
}
