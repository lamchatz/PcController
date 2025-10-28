package PcController.pccontroller.api;

import PcController.pccontroller.CommandRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AndroidAPI {
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/hey")
    public String hey() {
        return "hey";
    }

    @PostMapping("/age")
    public boolean age() {
        return runSafely(CommandRunner::startAge);
    }


    @PostMapping("/spotify")
    public boolean spotify() {
        return runSafely(CommandRunner::startSpotify);
    }

    private boolean runSafely(RunnableWithException action) {
        try {
            action.run();
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @FunctionalInterface
    interface RunnableWithException {
        void run() throws IOException, InterruptedException;
    }
}
