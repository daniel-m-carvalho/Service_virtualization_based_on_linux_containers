package org.example.cli;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static org.example.controllers.podman.CmdController.handleDeployCmd;
import static org.example.controllers.podman.HttpController.handleDeployHttp;

/**
 *  A class that handles command line interface (CLI) commands. This class contains functions
 *  to handle pod commands, Kubernetes commands, and help commands. The pod commands can be
 *  either HTTP commands or CMD commands, which are handled by the appropriate function.
 */
public class CLI {

    /**
     * A map that associates a boolean value with a function to handle pod commands.
     * If the boolean is true, it handles HTTP commands; otherwise, it handles CMD commands.
     */
    private static final Map<Boolean, Function<String [], Void>> POD_FUNC_MAP = Map.of(
            true, args -> { handleDeployHttp(args); return null; },
            false, args -> { handleDeployCmd(args); return null; }
    );

    /**
     * A map that associates a command string with a function to handle the command.
     */
    private static final Map<String, Function<String[], Void>> COMMANDS_MAP = Map.of(
            CommandsList.HELP, args -> { CLI.printHelp(); return null; },
            CommandsList.POD, args -> { CLI.handlePodCommand(args); return null; },
            CommandsList.KUBERNETES, args -> { CLI.handleKubernetesCommand(args); return null; }
    );

    /**
     * Runs the CLI with the specified arguments. If no arguments are provided, it prints the help message.
     *
     * @param args The command line arguments passed to the CLI.
     */
    public static void run(String... args) {
        COMMANDS_MAP.getOrDefault(
                args.length > 0 ? args[0] : CommandsList.HELP,
                arg -> {
                    printHelp();
                    return null;
                }
        ).apply(args);
    }

    /**
     * Handles pod commands by determining whether the command is an HTTP command
     * and then executing the appropriate function based on the command type.
     *
     * @param args The command line arguments passed to the pod command.
     */
    private static void handlePodCommand(String[] args) {
        boolean isHttpCommand = "http".equals(args[1]);
        int expectedArgsLength = isHttpCommand ? 5 : 4;

        if (args.length < expectedArgsLength) {
            return;
        }
        POD_FUNC_MAP.get(isHttpCommand).apply(args);
    }

    /**
     * Placeholder for handling Kubernetes commands. Currently prints a TODO message.
     *
     * @param args The command line arguments passed to the Kubernetes command.
     */
    private static void handleKubernetesCommand(String[] args) {
        System.out.println("TODO: Implement Kubernetes" + Arrays.toString(args));
    }

    /**
     * Prints help information to the console. This includes usage for pod commands,
     * Kubernetes commands, and the help command itself.
     */
    private static void printHelp() {
        System.out.println("Usage:");
        System.out.println(CommandsList.POD + " " + CommandsList.POD_CMD_FORMAT);
        System.out.println(CommandsList.POD + " " + CommandsList.POD_HTTP_FORMAT);
        System.out.println(CommandsList.KUBERNETES + " " + "TODO: Implement Kubernetes");
        System.out.println(CommandsList.HELP + "- Print this help message");
    }
}