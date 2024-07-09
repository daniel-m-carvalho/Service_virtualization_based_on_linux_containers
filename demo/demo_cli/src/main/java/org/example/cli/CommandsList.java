package org.example.cli;

/**
 * List of commands that can be executed by the CLI
 */
public class CommandsList {

    public static final String HELP = "help";
    public static final String POD = "pod";
    public static final String POD_CMD_FORMAT = "<pod-name> <image> <ports>";
    public static final String POD_HTTP_FORMAT = "http <pod-name> <image> <ports>";
    public static final String KUBERNETES = "kubernetes";
    public static final String KUBERNETES_FORMAT = "<kubernetes command>"; //TODO: Implement Kubernetes

}
