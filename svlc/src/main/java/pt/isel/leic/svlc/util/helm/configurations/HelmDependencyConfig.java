package pt.isel.leic.svlc.util.helm.configurations;

import java.nio.file.Path;

/**
 * Class representing configuration options for Helm dependency operations.
 */
public class HelmDependencyConfig {

    private Path keyring;   // Path to the keyring file
    private boolean skipRefresh = false;    // If true, do not refresh the local repository cache
    private boolean verify = false; // If true, verify the package against its signature
    private boolean debug = false;  // If true, enable verbose output

    /**
     * Constructor for the HelmDependencyConfig class.
     */
    public HelmDependencyConfig() {}

    /**
     * Getters and setters for all fields.
     */
    public Path getKeyring() {
        return keyring;
    }

    public void setKeyring(Path keyring) {
        this.keyring = keyring;
    }

    public boolean isKeyring() {
        return keyring != null;
    }

    public boolean isSkipRefresh() {
        return skipRefresh;
    }

    public void setSkipRefresh() {
        this.skipRefresh = true;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify() {
        this.verify = true;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug() {
        this.debug = true;
    }
}
