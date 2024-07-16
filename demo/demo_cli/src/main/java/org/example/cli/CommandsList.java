package org.example.cli;

/**
 * List of commands that can be executed by the CLI
 */
public class CommandsList {

    public static final String HELP = "help";
    public static final String POD = "pod";
    public static final String POD_CMD_DEPLOY_FORMAT = "deploy <pod-name> <image> <ports>(host:container)";
    public static final String POD_CMD_STATS_FORMAT = "stats <pod-name>";
    public static final String POD_HTTP_DEPLOY_FORMAT = "http deploy <pod-name> <image> <ports>(host:container)";
    public static final String POD_HTTP_STATS_FORMAT = "http stats <pod-name>";
    public static final String KUBERNETES = "kubernetes";
    public static final String KUBERNETES_DEPLOY_POD_FORMAT = "deploy <pod-name> <image> <ports>(Node:host:container)";
    public static final String KUBERNETES_POD_LOGS_FORMAT = "logs <pod-name>";
}
