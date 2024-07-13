package pt.isel.leic.svlc.util.helm.configurations;

import com.marcnuri.helm.UninstallCommand.Cascade;

import java.nio.file.Path;

/**
 * Represents the options for uninstalling a Helm chart.
 */
//public class HelmUninstallConfig {

//    private String chartReference;  // The reference to the chart
//    private boolean dryRun = false; // If true, simulate an uninstallation
//    private boolean noHooks = false;    // If true, prevent hooks from running during uninstall
//    private boolean ignoreNotFound = false; // If true, ignore the not found error for the release
//    private boolean keepHistory = false;// If true, remove all associated resources and mark the release as deleted
//    private Cascade cascade; // Determines whether resources should be deleted in the same order they were created
//    private String namespace;   // The namespace scope for this request
//    private Path kubeConfig;    // The path to the kube config file
//    private boolean debug = false;  // If true, enable verbose output
//
//    /**
//     * Constructs a HelmUninstallOptions object with a chart reference.
//     * @param chartReference The reference to the chart.
//     */
//    public HelmUninstallConfig(String chartReference) {
//        this.chartReference = chartReference;
//    }
//
//    /**
//     * Getters and setters for all fields
//     */
//    public String getChartReference() {
//        return chartReference;
//    }
//
//    public void setChartReference(String chartReference) {
//        this.chartReference = chartReference;
//    }
//
//    public boolean isDryRun() {
//        return dryRun;
//    }
//
//    public void setDryRun() {
//        this.dryRun = true;
//    }
//
//    public boolean isNoHooks() {
//        return noHooks;
//    }
//
//    public void setNoHooks() {
//        this.noHooks = true;
//    }
//
//    public boolean isIgnoreNotFound() {
//        return ignoreNotFound;
//    }
//
//    public void setIgnoreNotFound() {
//        this.ignoreNotFound = true;
//    }
//
//    public boolean isKeepHistory() {
//        return keepHistory;
//    }
//
//    public void setKeepHistory() {
//        this.keepHistory = true;
//    }
//
//    public Cascade getCascade() {
//        return cascade;
//    }
//
//    public void setCascade(Cascade cascade) {
//        this.cascade = cascade;
//    }
//
//    public boolean isCascade() {
//        return cascade != null;
//    }
//
//    public String getNamespace() {
//        return namespace;
//    }
//
//    public void setNamespace(String namespace) {
//        this.namespace = namespace;
//    }
//
//    public boolean isNamespace() {
//        return namespace != null;
//    }
//
//    public Path getKubeConfig() {
//        return kubeConfig;
//    }
//
//    public void setKubeConfig(Path kubeConfig) {
//        this.kubeConfig = kubeConfig;
//    }
//
//    public boolean isKubeConfig() {
//        return kubeConfig != null;
//    }
//
//    public boolean isDebug() {
//        return debug;
//    }
//
//    public void setDebug() {
//        this.debug = true;
//    }
//}
