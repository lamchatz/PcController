package PcController.pccontroller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandRunner {

    public static final String CMD_EXE = "cmd.exe";
    public static final String C = "/c";
    public static final String CONTROLLER_LOG = "controller.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private CommandRunner() {
    }

    private static void log(String message) {
        try (FileWriter fw = new FileWriter(CONTROLLER_LOG, true)) { // append mode
            String timeStampedMessage = String.format("[%s] %s%n", LocalDateTime.now().format(formatter), message);
            fw.write(timeStampedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runCommand(String command) throws IOException, InterruptedException {
        log("Starting command: " + command);
        ProcessBuilder processBuilder = new ProcessBuilder(CMD_EXE, C, command);
        processBuilder.redirectErrorStream(true);
        File logFile = new File(CONTROLLER_LOG);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));

        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            log("Command succeeded: " + command);
        } else {
            log("Command failed (exit code " + exitCode + "): " + command);
        }
    }

    public static void startSpotify() throws IOException, InterruptedException {
        runCommand("cd C:\\ && start Spotify.lnk");
    }

    public static void startAge() throws IOException, InterruptedException {
        runCommand("cd C:\\ && start age.url && start Discord.lnk");
    }

}