package org.example.cli;

/**
 * List of commands that can be executed by the CLI
 */
public class CommandsList {

    public static final String HELP = "help";
    public static final String POD = "pod";
    public static final String POD_CMD_DEPLOY_FORMAT = "deploy <config-file>";
    public static final String POD_CMD_STATS_FORMAT = "stats <pod-name>";
    public static final String POD_HTTP_DEPLOY_FORMAT = "http deploy <config-file>";
    public static final String POD_HTTP_STATS_FORMAT = "http stats <pod-name>";
    public static final String KUBERNETES = "kube";
    public static final String KUBERNETES_DEPLOY_POD_FORMAT = "deploy <config-file>";
    public static final String KUBERNETES_POD_LOGS_FORMAT = "logs <cluster-name> <pod-name>";
}
