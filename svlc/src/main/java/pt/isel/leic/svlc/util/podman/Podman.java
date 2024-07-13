package pt.isel.leic.svlc.util.podman;

/**
 * This interface defines the operations that a Podman runner should support.
 * The Podman runner can be a command line tool or an HTTP client.
 * The operations include creating, starting, stopping, and managing pods and containers.
 * As well as pulling and deleting images.
 */
public interface Podman {
    String createPod() throws Exception;
    String startPod() throws Exception;
    String stopPod() throws Exception;
    String prunePods() throws Exception;
    String getPodStatistics() throws Exception;
    String createContainer() throws Exception;
    String pullImage() throws Exception;
    String deleteImage() throws Exception;
}
