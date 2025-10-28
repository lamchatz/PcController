package PcController.pccontroller.api;

import PcController.pccontroller.CommandRunner;
import PcController.pccontroller.enums.URL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AndroidAPI {
    @GetMapping("/test")
    public boolean test() {
        return true;
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

    @PostMapping("/open/{name}")
    public boolean open (@PathVariable String name){
        try {
            URL url = URL.valueOf(name.toUpperCase());

            return runSafely(() -> CommandRunner.openChromeTab(url.getLink()));
        } catch (IllegalArgumentException e) {
            return false;
        }
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
