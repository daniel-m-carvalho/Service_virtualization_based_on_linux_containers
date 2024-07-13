package pt.isel.leic.svlc.util.helm.configurations;

//import com.marcnuri.helm.DryRun;

import java.nio.file.Path;
import java.util.Map;

/**
 * Represents the options for the Helm upgrade command.
 */
//public class HelmUpgradeConfig {

//    private String chartReference;  // The reference to the chart
//    private String releaseName; // The name of the release
//    private String namespace;   // The namespace scope for this request
//    private boolean install = false;    // If true, install the chart if it doesn't exist
//    private boolean force = false;  // If true, force resource updates through a replacement strategy
//    private boolean resetValues = false;    // If true, reset the values to the ones built into the chart
//    private boolean reuseValues = false;    // If true, reuse the last release's values and merge in any new values
//    private boolean resetThenReuseValues = false;   // If true, reset the values and then reuse the last release's values
//    private boolean atomic = false; // If true, upgrade process rolls back changes made in case of failed upgrade
//    private boolean cleanupOnFail = false;  // If true, allow deletion of new resources created in this upgrade when upgrade fails
//    private String description; // The description of the release
//    private boolean devel = false;  // If true, use development versions, too (alpha, beta, and release candidate releases)
//    private boolean dependencyUpdate = false;   // If true, run helm dependency update before installing the chart
//    private boolean disableOpenApiValidation = false;   // If true, disable openapi validation when installing the chart
//    private boolean dryRun = false; // If true, simulate an upgrade
//    private DryRun dryRunOption;    // The dry run option
//    private boolean waitReady = false; // If true, wait until everything is in a ready state before marking the release as successful
//    private Map<String, Object> values; // The values to be used in the upgrade
//    private Path kubeConfig;    // The path to the kube config file
//    private Path certFile;  // The path to the certificate file for the TLS connection to the server
//    private Path keyFile;   // The path to the key file for the TLS connection to the server
//    private Path caFile;    // The path to the CA file for the TLS connection to the server
//    private boolean insecureSkipTlsVerify = false;  // If true, skip the TLS verification
//    private boolean plainHttp = false;  // If true, use plain HTTP requests
//    private Path keyring;   // The path to the keyring containing public keys for verification
//    private boolean debug = false;  // If true, enable verbose output
//
//    /**
//     * Constructs a HelmUpgradeOptions object with no arguments.
//     */
//    public HelmUpgradeConfig(){}
//
//    /**
//     * Constructs a HelmUpgradeOptions object with a chart reference.
//     * @param chartReference The reference to the chart.
//     */
//    public HelmUpgradeConfig(String chartReference) {
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
//    public boolean isChartReference() {
//        return chartReference != null && !chartReference.isEmpty();
//    }
//
//    public String getReleaseName() {
//        return releaseName;
//    }
//
//    public void setReleaseName(String releaseName) {
//        this.releaseName = releaseName;
//    }
//
//    public boolean isReleaseName() {
//        return releaseName != null && !releaseName.isEmpty();
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
//    public boolean isCreateNamespace() {
//        return releaseName != null && !releaseName.isEmpty();
//    }
//
//    public boolean isInstall() {
//        return install;
//    }
//
//    public void setInstall() {
//        this.install = true;
//    }
//
//    public boolean isForce() {
//        return force;
//    }
//
//    public void setForce() {
//        this.force = true;
//    }
//
//    public boolean isResetValues() {
//        return resetValues;
//    }
//
//    public void setResetValues() {
//        this.resetValues = true;
//    }
//
//    public boolean isReuseValues() {
//        return reuseValues;
//    }
//
//    public void setReuseValues() {
//        this.reuseValues = true;
//    }
//
//    public boolean isResetThenReuseValues() {
//        return resetThenReuseValues;
//    }
//
//    public void setResetThenReuseValues() {
//        this.resetThenReuseValues = true;
//    }
//
//    public boolean isAtomic() {
//        return atomic;
//    }
//
//    public void setAtomic() {
//        this.atomic = true;
//    }
//
//    public boolean isCleanupOnFail() {
//        return cleanupOnFail;
//    }
//
//    public void setCleanupOnFail() {
//        this.cleanupOnFail = true;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public boolean isDescription() {
//        return description != null && !description.isEmpty();
//    }
//
//    public boolean isDevel() {
//        return devel;
//    }
//
//    public void setDevel() {
//        this.devel = true;
//    }
//
//    public boolean isDependencyUpdate() {
//        return dependencyUpdate;
//    }
//
//    public void setDependencyUpdate() {
//        this.dependencyUpdate = true;
//    }
//
//    public boolean isDisableOpenApiValidation() {
//        return disableOpenApiValidation;
//    }
//
//    public void setDisableOpenApiValidation() {
//        this.disableOpenApiValidation = true;
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
//    public DryRun getDryRunOption() {
//        return dryRunOption;
//    }
//
//    public void setDryRunOption(DryRun dryRunOption) {
//        this.dryRunOption = dryRunOption;
//    }
//
//    public boolean isDryRunOption() {
//        return dryRunOption != null;
//    }
//
//    public boolean isWaitReady() {
//        return waitReady;
//    }
//
//    public void setWaitReady() {
//        this.waitReady = true;
//    }
//
//    public Map<String, Object> getValues() {
//        return values;
//    }
//
//    public void setValues(Map<String, Object> values) {
//        this.values = values;
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
//    public Path getCertFile() {
//        return certFile;
//    }
//
//    public void setCertFile(Path certFile) {
//        this.certFile = certFile;
//    }
//
//    public boolean isCertFile() {
//        return certFile != null;
//    }
//
//    public Path getKeyFile() {
//        return keyFile;
//    }
//
//    public void setKeyFile(Path keyFile) {
//        this.keyFile = keyFile;
//    }
//
//    public boolean isKeyFile() {
//        return keyFile != null;
//    }
//
//    public Path getCaFile() {
//        return caFile;
//    }
//
//    public void setCaFile(Path caFile) {
//        this.caFile = caFile;
//    }
//
//    public boolean isCaFile() {
//        return caFile != null;
//    }
//
//    public boolean isInsecureSkipTlsVerify() {
//        return insecureSkipTlsVerify;
//    }
//
//    public void setInsecureSkipTlsVerify() {
//        this.insecureSkipTlsVerify = true;
//    }
//
//    public boolean isPlainHttp() {
//        return plainHttp;
//    }
//
//    public void setPlainHttp() {
//        this.plainHttp = true;
//    }
//
//    public Path getKeyring() {
//        return keyring;
//    }
//
//    public void setKeyring(Path keyring) {
//        this.keyring = keyring;
//    }
//
//    public boolean isKeyring() {
//        return keyring != null;
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