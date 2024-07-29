package pt.isel.leic.svlc.util.podman;

/**
 * This interface defines the operations that a Podman runner should support.
 * The Podman runner can be a command line tool or an HTTP client.
 * The operations include creating, starting, stopping, and managing pods and containers.
 * As well as pulling and deleting images.
 */
public interface Podman<T> {
    T createPod() throws Exception;
    T startPod() throws Exception;
    T stopPod() throws Exception;
    T deletePod() throws Exception;
    T inspectPod() throws Exception;
    T deleteContainer(String name) throws Exception;
    T pullImage() throws Exception;
    T deleteImage() throws Exception;
}
