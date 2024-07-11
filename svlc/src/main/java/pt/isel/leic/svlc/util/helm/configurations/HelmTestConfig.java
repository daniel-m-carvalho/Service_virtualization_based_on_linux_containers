package pt.isel.leic.svlc.util.helm.configurations;

import java.nio.file.Path;

/**
 * Class representing options for testing a Helm chart.
 */
public class HelmTestConfig {

    private String chartReference;  // The reference to the chart.
    private int timeout = 0;    // The timeout for the test.
    private String namespace;   // The namespace to use for the test.
    private Path kubeConfig;    // The path to the kube config file.
    private boolean debug = false;  // If true, enable verbose output.

    /**
     * Constructs a HelmTestConfig object with the given chart reference.
     * @param chartReference The reference to the chart.
     */
    public HelmTestConfig(String chartReference) {
        this.chartReference = chartReference;
    }

    /**
     * Getters and setters for all fields.
     */
    public String getChartReference() {
        return chartReference;
    }

    public void setChartReference(String chartReference) {
        this.chartReference = chartReference;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isTimeout() {
        return timeout > 0;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public boolean isNamespace() {
        return namespace != null && !namespace.isEmpty();
    }

    public Path getKubeConfig() {
        return kubeConfig;
    }

    public void setKubeConfig(Path kubeConfig) {
        this.kubeConfig = kubeConfig;
    }

    public boolean isKubeConfig() {
        return kubeConfig != null;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug() {
        this.debug = true;
    }
}
