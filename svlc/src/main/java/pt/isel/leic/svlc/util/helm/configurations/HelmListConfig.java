package pt.isel.leic.svlc.util.helm.configurations;

import java.nio.file.Path;

/**
 * Class representing options for listing Helm releases.
 */
//public class HelmListConfig {

//    private String namespace; // Namespace
//    private Path kubeConfig; // Path to the kube config file
//    private boolean all = false; // If true, list all releases
//    private boolean allNamespaces = false; // If true, List releases in all namespaces
//    private boolean deployed = false; // If true, List deployed releases
//    private boolean failed = false; // If true, List failed releases
//    private boolean pending = false; // If true, List pending releases
//    private boolean superseded = false; // If true, List superseded releases
//    private boolean uninstalled = false; // If true, List uninstalled releases
//    private boolean uninstalling = false; // If true, List uninstalling releases
//
//    /**
//     * Constructs a HelmListConfig object with no parameters.
//     */
//    public HelmListConfig() {}
//
//    /**
//     * Getters and setters for all fields.
//     */
//    public String getNamespace() {
//        return namespace;
//    }
//
//    public void setNamespace(String namespace) {
//        this.namespace = namespace;
//    }
//
//    public boolean isNamespace() {
//        return namespace != null && !namespace.isEmpty();
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
//    public boolean isAll() {
//        return all;
//    }
//
//    public void setAll() {
//        this.all = true;
//    }
//
//    public boolean isAllNamespaces() {
//        return allNamespaces;
//    }
//
//    public void setAllNamespaces() {
//        this.allNamespaces = true;
//    }
//
//    public boolean isDeployed() {
//        return deployed;
//    }
//
//    public void setDeployed() {
//        this.deployed = true;
//    }
//
//    public boolean isFailed() {
//        return failed;
//    }
//
//    public void setFailed() {
//        this.failed = true;
//    }
//
//    public boolean isPending() {
//        return pending;
//    }
//
//    public void setPending() {
//        this.pending = true;
//    }
//
//    public boolean isSuperseded() {
//        return superseded;
//    }
//
//    public void setSuperseded() {
//        this.superseded = true;
//    }
//
//    public boolean isUninstalled() {
//        return uninstalled;
//    }
//
//    public void setUninstalled(boolean uninstalled) {
//        this.uninstalled = true;
//    }
//
//    public boolean isUninstalling() {
//        return uninstalling;
//    }
//
//    public void setUninstalling() {
//        this.uninstalling = true;
//    }
//}