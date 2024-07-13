package pt.isel.leic.svlc.util.executers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static pt.isel.leic.svlc.util.executers.CommandResult.fromOutput;
import static pt.isel.leic.svlc.util.executers.CommandResult.fromThread;

/**
 *  This class executes a command using a ProcessBuilder.
 *  It collects the output and error of the command and
 *  waits for the command to finish.
 *  If the command fails, it throws an exception.
 */

public class CmdExec {

    /**
     * Executes a command using a ProcessBuilder.
     *
     * @param command The command to be executed.
     * @return The output of the command.
     * @throws Exception If the command fails.
     */
    public static CommandResult executeCommand(String command, String successMessage, boolean wait) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        if (wait){
            return fromOutput(synchronousExec(processBuilder, successMessage));
        }else {
            return fromThread(asynchronousExec(processBuilder));
        }
    }

    private static Thread asynchronousExec(ProcessBuilder processBuilder) {
        Thread thread = new Thread( () -> {
            try {
                Process process = processBuilder.start();
                process.waitFor();
            } catch (Exception e) {
                System.err.println("[ERROR]: " + e.getMessage());
            }
        });
        thread.start();
        return thread;
    }

    private static String synchronousExec(ProcessBuilder processBuilder, String successMessage) throws Exception {
        Process process = processBuilder.start();

        // Collect the output of the command
        String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines().collect(Collectors.joining("\n"));

        // Collect the error of the command
        String error = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                .lines().collect(Collectors.joining("\n"));
        if (!error.isEmpty()) {
            System.out.println("Error: " + error);
        }

        // Wait for the command to finish
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new Exception("Command failed with exit code " + exitCode + "\n" + error);
        }

        if (successMessage != null && output.isEmpty()) return successMessage;
        else return output;
    }
}
